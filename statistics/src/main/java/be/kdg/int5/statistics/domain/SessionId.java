package be.kdg.int5.statistics.domain;

import java.util.Objects;
import java.util.UUID;

public record SessionId(UUID uuid) {
    public SessionId {
        Objects.requireNonNull(uuid);
    }
}
