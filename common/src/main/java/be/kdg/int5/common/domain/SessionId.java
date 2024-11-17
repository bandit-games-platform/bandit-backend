package be.kdg.int5.common.domain;

import java.util.Objects;
import java.util.UUID;

public record SessionId(UUID uuid) {
    public SessionId {
        Objects.requireNonNull(uuid);
    }
}
