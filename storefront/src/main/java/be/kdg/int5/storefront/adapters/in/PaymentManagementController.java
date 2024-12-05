package be.kdg.int5.storefront.adapters.in;

import be.kdg.int5.common.exceptions.OrderAlreadyExistsException;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.*;
import be.kdg.int5.storefront.port.in.query.GameBasicDetailsQuery;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
public class PaymentManagementController {
    @Value("${stripe.apiKey}")
    private String apiKey;
    private final SaveNewOrderUseCase saveNewOrderUseCase;
    private final CompleteOrderUseCase completeOrderUseCase;
    private final GameBasicDetailsQuery gameBasicDetailsQuery;
    private final CreateStripeSessionUseCase createStripeSessionUseCase;

    public PaymentManagementController(
            SaveNewOrderUseCase saveNewOrderUseCase,
            CompleteOrderUseCase completeOrderUseCase,
            GameBasicDetailsQuery gameBasicDetailsQuery,
            CreateStripeSessionUseCase createStripeSessionUseCase
    ) {
        this.saveNewOrderUseCase = saveNewOrderUseCase;
        this.completeOrderUseCase = completeOrderUseCase;
        this.gameBasicDetailsQuery = gameBasicDetailsQuery;
        this.createStripeSessionUseCase = createStripeSessionUseCase;
    }

    @PostMapping("/games/{gameId}/create-order")
    @PreAuthorize("hasAuthority('player')")
    public Map<String, String> createCheckoutSession(
            @AuthenticationPrincipal Jwt token,
            @PathVariable String gameId
    ) {
        UUID customerId = UUID.fromString(token.getClaimAsString("sub"));
        UUID productId = UUID.fromString(gameId);

        Stripe.apiKey = apiKey;

        try {
            ProductProjection basicGameDetails = gameBasicDetailsQuery.getBasicGameDetails(productId);
            if (basicGameDetails == null) return null;

            Session session = createStripeSessionUseCase.createStripeSession(new CreateStripeSessionCommand(
                    basicGameDetails, productId, gameId
            ));

            try {
                Order order = saveNewOrderUseCase.saveNewOrder(new SaveNewOrderCommand(
                        session.getId(), productId, customerId
                ));
            } catch (OrderAlreadyExistsException e) {
                return null;
            }

            Map<String, String> map = new HashMap();
            map.put("clientSecret", session.getClientSecret());
            return map;
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/games/{gameId}/order-status")
    public Map<String, String> getOrderSessionStatus(
            @AuthenticationPrincipal Jwt token,
            @PathVariable String gameId,
            @RequestParam String sessionId
    ) {
        Stripe.apiKey = apiKey;

        UUID customerId = UUID.fromString(token.getClaimAsString("sub"));
        UUID productId = UUID.fromString(gameId);
        try {
            Session session = Session.retrieve(sessionId);

            if (Objects.equals(session.getStatus(), "complete")) {
                boolean completed = completeOrderUseCase.completeOrder(new CompleteOrderCommand(
                        sessionId, productId, customerId
                ));
                if (!completed) return null;
            }

            Map<String, String> map = new HashMap();
            map.put("status", session.getStatus());

            return map;
        } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
