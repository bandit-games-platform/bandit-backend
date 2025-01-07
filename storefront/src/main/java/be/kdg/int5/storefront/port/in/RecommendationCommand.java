package be.kdg.int5.storefront.port.in;

import be.kdg.int5.storefront.domain.CustomerId;

import java.util.Objects;

public record RecommendationCommand(CustomerId customerId) {
    public RecommendationCommand {
        Objects.requireNonNull(customerId);
    }
}
