package be.kdg.int5.storefront.adapters.out.db;

import be.kdg.int5.storefront.domain.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "storefront", name = "order")
public class OrderJpaEntity {
    @Id
    private UUID id;
    private UUID productId;
    private UUID customerId;
    private LocalDateTime orderDate;
    private LocalDateTime orderCompletedAt;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderJpaEntity() {
    }

    public OrderJpaEntity(UUID id, UUID productId, UUID customerId, LocalDateTime orderDate, OrderStatus orderStatus) {
        this(id, productId, customerId, orderDate, null, orderStatus);
    }

    public OrderJpaEntity(UUID id, UUID productId, UUID customerId, LocalDateTime orderDate, LocalDateTime orderCompletedAt, OrderStatus orderStatus) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderCompletedAt = orderCompletedAt;
        this.orderStatus = orderStatus;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getOrderCompletedAt() {
        return orderCompletedAt;
    }

    public void setOrderCompletedAt(LocalDateTime orderCompletedAt) {
        this.orderCompletedAt = orderCompletedAt;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
