package be.kdg.int5.common.events;

import java.util.Objects;
import java.util.UUID;

public record OrderCompletedCommand(
        UUID orderId,
        UUID customerId,
        UUID productId
) {
    public OrderCompletedCommand {
        Objects.requireNonNull(orderId);
        Objects.requireNonNull(customerId);
        Objects.requireNonNull(productId);
    }
}
