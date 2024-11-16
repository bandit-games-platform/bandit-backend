package be.kdg.int5.storefront.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private OrderId id;
    private ProductId productId;
    private CustomerId customerId;
    private LocalDateTime orderDate;
    private LocalDateTime  orderCompletedAt;
    private OrderStatus orderStatus;

    public Order( ProductId productId, CustomerId customerId, LocalDateTime orderDate) {
        id = new OrderId(UUID.randomUUID());
        Objects.requireNonNull(id);
        Objects.requireNonNull(productId);
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(orderDate);

        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
}
