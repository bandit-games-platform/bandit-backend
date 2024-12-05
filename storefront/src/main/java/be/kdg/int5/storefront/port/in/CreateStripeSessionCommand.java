package be.kdg.int5.storefront.port.in;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.UUID;

public record CreateStripeSessionCommand(ProductProjection basicGameDetails, UUID productId, String gameId) {
}
