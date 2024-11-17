package be.kdg.int5.storefront.domain;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record OrderId(UUID uuid) {
    public OrderId {
        requireNonNull(uuid);
    }
}
