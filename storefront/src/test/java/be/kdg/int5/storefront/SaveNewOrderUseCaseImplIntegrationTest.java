package be.kdg.int5.storefront;

import be.kdg.int5.common.exceptions.OrderAlreadyExistsException;
import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaEntity;
import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaRepository;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.OrderStatus;
import be.kdg.int5.storefront.port.in.SaveNewOrderCommand;
import be.kdg.int5.storefront.port.in.SaveNewOrderUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SaveNewOrderUseCaseImplIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private SaveNewOrderUseCase saveNewOrderUseCase;
    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Test
    void whenPlayerCreatesOrderForGameTheyDoNotHaveACompletedOrderForOrderIsCreated() {
        // Arrange

        // Act
        Order order = saveNewOrderUseCase.saveNewOrder(new SaveNewOrderCommand(
            Variables.STRIPE_SESSION_ID,
            Variables.GAME_ID,
            Variables.PLAYER_ONE_ID
        ));

        // Assert
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findPendingByProductIdAndCustomerIdAndStripeSessionId(
                Variables.STRIPE_SESSION_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_ID
        );

        assertNotNull(orderJpaEntity);
        assertEquals(OrderStatus.PENDING, orderJpaEntity.getOrderStatus());
        assertEquals(Variables.STRIPE_SESSION_ID, orderJpaEntity.getStripeSessionId());
        assertEquals(Variables.GAME_ID, orderJpaEntity.getProductId());
        assertEquals(Variables.PLAYER_ONE_ID, orderJpaEntity.getCustomerId());
        assertEquals(LocalDateTime.now().getDayOfMonth(), orderJpaEntity.getOrderDate().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonthValue(), orderJpaEntity.getOrderDate().getMonthValue());
        assertEquals(LocalDateTime.now().getYear(), orderJpaEntity.getOrderDate().getYear());

        assertNotNull(order);
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertEquals(Variables.STRIPE_SESSION_ID, order.getStripeSessionId());
        assertEquals(Variables.GAME_ID, order.getProductId().uuid());
        assertEquals(Variables.PLAYER_ONE_ID, order.getCustomerId().uuid());
        assertEquals(LocalDateTime.now().getDayOfMonth(), order.getOrderDate().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonthValue(), order.getOrderDate().getMonthValue());
        assertEquals(LocalDateTime.now().getYear(), order.getOrderDate().getYear());

        orderJpaRepository.deleteById(orderJpaEntity.getId());
    }

    @Test
    void whenPlayerCreatesOrderForGameTheyHaveACompletedOrderForExceptionIsThrown() {
        // Arrange
        OrderJpaEntity orderJpaEntity = orderJpaRepository.save(new OrderJpaEntity(
                UUID.randomUUID(),
                Variables.STRIPE_SESSION_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_ID,
                LocalDateTime.now(),
                OrderStatus.COMPLETED
        ));

        // Act
        Executable test = () -> saveNewOrderUseCase.saveNewOrder(new SaveNewOrderCommand(
                Variables.STRIPE_SESSION_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_ID
        ));

        // Assert
        assertThrows(OrderAlreadyExistsException.class, test);

        orderJpaRepository.deleteById(orderJpaEntity.getId());
    }
}
