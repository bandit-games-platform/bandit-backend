package be.kdg.int5.storefront.domain;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record ProductId(UUID uuid) {
    public ProductId {
        requireNonNull(uuid);
    }
}
