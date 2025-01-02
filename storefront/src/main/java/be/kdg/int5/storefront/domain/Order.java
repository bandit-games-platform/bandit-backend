package be.kdg.int5.storefront.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final OrderId id;
    private final String stripeSessionId;
    private final ProductId productId;
    private final CustomerId customerId;
    private final LocalDateTime orderDate;
    private LocalDateTime  orderCompletedAt;
    private OrderStatus orderStatus;

    public Order(ProductId productId, CustomerId customerId, LocalDateTime orderDate, String stripeSessionId) {
        this(new OrderId(UUID.randomUUID()), stripeSessionId, productId, customerId, orderDate, OrderStatus.PENDING, null);
    }

    public Order(
            OrderId id, String stripeSessionId,
            ProductId productId,
            CustomerId customerId,
            LocalDateTime orderDate,
            OrderStatus orderStatus,
            LocalDateTime orderCompletedAt
    ) {
        this.stripeSessionId = stripeSessionId;
        Objects.requireNonNull(id);
        Objects.requireNonNull(productId);
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(orderDate);

        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.orderCompletedAt = orderCompletedAt;
    }

    public OrderId getId() {
        return id;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public ProductId getProductId() {
        return productId;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
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

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    public void completeOrder() {
        this.orderStatus = OrderStatus.COMPLETED;
        this.orderCompletedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productId=" + productId +
                ", customerId=" + customerId +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
