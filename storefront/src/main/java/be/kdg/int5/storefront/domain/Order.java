package be.kdg.int5.storefront.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private OrderId id;
    private ProductId productId;
    private CustomerId customerId;
    private LocalDateTime orderDate;
    private LocalDateTime  orderCompletedAt;
    private OrderStatus orderStatus;

    public Order(OrderId id, ProductId productId, CustomerId customerId, LocalDateTime orderDate) {
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
