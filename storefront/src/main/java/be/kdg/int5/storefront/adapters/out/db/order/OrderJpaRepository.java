package be.kdg.int5.storefront.adapters.out.db.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    @Query("""
    SELECT orderEntity FROM OrderJpaEntity orderEntity
    WHERE orderEntity.productId = :productId
    AND orderEntity.customerId = :customerId
    AND orderEntity.orderStatus = 'COMPLETED'
    """)
    OrderJpaEntity findCompletedByProductIdAndCustomerId(UUID productId, UUID customerId);

    @Query("""
    SELECT orderEntity FROM OrderJpaEntity orderEntity
    WHERE orderEntity.productId = :productId
    AND orderEntity.customerId = :customerId
    AND orderEntity.stripeSessionId = :sessionId
    """)
    OrderJpaEntity findByProductIdAndCustomerIdAndStripeSessionId(String sessionId, UUID productId, UUID customerId);

    @Query("""
    SELECT orderEntity FROM OrderJpaEntity orderEntity
    WHERE orderEntity.orderStatus = 'PENDING'
    """)
    List<OrderJpaEntity> findAllPendingOrders();

    @Query("""
    SELECT orderEntity FROM OrderJpaEntity orderEntity
    WHERE orderEntity.orderStatus = 'COMPLETED'
    """)
    List<OrderJpaEntity> findAllCompleteOrders();

    @Query("""
    SELECT orderEntity FROM OrderJpaEntity orderEntity
    WHERE orderEntity.customerId = :customerId
    AND orderEntity.orderStatus = 'COMPLETED'
    """)
    List<OrderJpaEntity> findAllCompletedOrdersByCustomerId(UUID customerId);
}
