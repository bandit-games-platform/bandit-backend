package be.kdg.int5.storefront;

import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaEntity;
import be.kdg.int5.storefront.adapters.out.db.order.OrderJpaRepository;
import be.kdg.int5.storefront.domain.OrderStatus;
import be.kdg.int5.storefront.port.in.CompleteOrderCommand;
import be.kdg.int5.storefront.port.in.CompleteOrderUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompleteOrderUseCaseImplIntegrationTest extends AbstractDatabaseTest {
    @Autowired
    private CompleteOrderUseCase completeOrderUseCase;
    @Autowired
    private OrderJpaRepository orderJpaRepository;
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void whenOrderWithPendingStatusExistsUpdateOrderStatusToComplete() {
        // Arrange
        OrderJpaEntity orderJpaEntity = orderJpaRepository.save(new OrderJpaEntity(
                UUID.randomUUID(),
                Variables.STRIPE_SESSION_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_ID,
                LocalDateTime.now(),
                OrderStatus.PENDING
        ));

        // Act
        boolean orderCompleted = completeOrderUseCase.completeOrder(
                new CompleteOrderCommand(
                        Variables.STRIPE_SESSION_ID,
                        Variables.GAME_ID,
                        Variables.PLAYER_ONE_ID
                )
        );

        // Assert
        OrderJpaEntity updatedOrderJpaEntity = orderJpaRepository.findPendingByProductIdAndCustomerIdAndStripeSessionId(
                Variables.STRIPE_SESSION_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_ID
        );

        assertTrue(orderCompleted);

        assertNotNull(updatedOrderJpaEntity);
        assertEquals(OrderStatus.COMPLETED, updatedOrderJpaEntity.getOrderStatus());
        assertEquals(Variables.STRIPE_SESSION_ID, updatedOrderJpaEntity.getStripeSessionId());
        assertEquals(Variables.GAME_ID, updatedOrderJpaEntity.getProductId());
        assertEquals(Variables.PLAYER_ONE_ID, updatedOrderJpaEntity.getCustomerId());
        assertEquals(LocalDateTime.now().getDayOfMonth(), updatedOrderJpaEntity.getOrderDate().getDayOfMonth());
        assertEquals(LocalDateTime.now().getMonthValue(), updatedOrderJpaEntity.getOrderDate().getMonthValue());
        assertEquals(LocalDateTime.now().getYear(), updatedOrderJpaEntity.getOrderDate().getYear());

        orderJpaRepository.deleteById(orderJpaEntity.getId());
    }

    @Test
    void whenOrderWithCompleteStatusExistsShouldReturnTrue() {
        // Arrange
        OrderJpaEntity orderJpaEntity = orderJpaRepository.save(new OrderJpaEntity(
                UUID.randomUUID(),
                Variables.STRIPE_SESSION_ID,
                Variables.GAME_ID,
                Variables.PLAYER_ONE_ID,
                LocalDateTime.now().minusSeconds(5),
                LocalDateTime.now(),
                OrderStatus.COMPLETED
        ));

        // Act
        boolean orderCompleted = completeOrderUseCase.completeOrder(
                new CompleteOrderCommand(
                        Variables.STRIPE_SESSION_ID,
                        Variables.GAME_ID,
                        Variables.PLAYER_ONE_ID
                )
        );

        // Assert
        assertTrue(orderCompleted);

        orderJpaRepository.deleteById(orderJpaEntity.getId());
    }

    @Test
    void whenOrderDoesNotExistShouldReturnFalse() {
        // Arrange

        // Act
        boolean orderCompleted = completeOrderUseCase.completeOrder(
                new CompleteOrderCommand(
                        Variables.STRIPE_SESSION_ID,
                        Variables.GAME_ID,
                        Variables.PLAYER_ONE_ID
                )
        );

        // Assert
        assertFalse(orderCompleted);
    }
}
