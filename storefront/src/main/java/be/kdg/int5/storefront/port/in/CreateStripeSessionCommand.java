package be.kdg.int5.storefront.port.in;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.UUID;

public record CreateStripeSessionCommand(UUID productId, String gameId) {
}
