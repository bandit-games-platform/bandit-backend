package be.kdg.int5.storefront.adapters.in;

import be.kdg.int5.common.exceptions.OrderAlreadyExistsException;
import be.kdg.int5.storefront.adapters.in.dto.CreatedOrderIdDto;
import be.kdg.int5.storefront.adapters.in.dto.NewOrderDto;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.port.in.SaveNewOrderCommand;
import be.kdg.int5.storefront.port.in.SaveNewOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderRestController {
    private final SaveNewOrderUseCase saveNewOrderUseCase;

    public OrderRestController(SaveNewOrderUseCase saveNewOrderUseCase) {
        this.saveNewOrderUseCase = saveNewOrderUseCase;
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<CreatedOrderIdDto> submitNewOrder(
            @AuthenticationPrincipal Jwt token,
            @Valid @RequestBody NewOrderDto dto
            ){
        UUID customerId = UUID.fromString(token.getClaimAsString("sub"));

        try {
            Order order = saveNewOrderUseCase.saveNewOrder(new SaveNewOrderCommand(
                    dto.getProductId(), customerId
            ));
            return ResponseEntity.ok(new CreatedOrderIdDto(order.getId().uuid()));
        } catch (OrderAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
    }

}
