package be.kdg.int5.storefront.port.in;

import java.util.UUID;

public record CompleteOrderCommand(UUID productId, UUID customerId) {
}
