package be.kdg.int5.common.domain;

import java.util.Objects;
import java.util.UUID;

public record PlayerId(UUID uuid) {
    public PlayerId {
        Objects.requireNonNull(uuid);
    }
}
