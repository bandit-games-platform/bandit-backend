package be.kdg.int5.storefront.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Order {
    private final OrderId id;
    private final ProductId productId;
    private final CustomerId customerId;
    private final LocalDateTime orderDate;
    private LocalDateTime  orderCompletedAt;
    private OrderStatus orderStatus;

    public Order( ProductId productId, CustomerId customerId, LocalDateTime orderDate) {
        id = new OrderId(UUID.randomUUID());
        Objects.requireNonNull(id);
        Objects.requireNonNull(productId);
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(orderDate);

        this.productId = productId;
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
}
