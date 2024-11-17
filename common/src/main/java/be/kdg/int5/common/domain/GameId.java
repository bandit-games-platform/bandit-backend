package be.kdg.int5.common.domain;

import java.util.Objects;
import java.util.UUID;

public record GameId(UUID uuid) {
    public GameId {
        Objects.requireNonNull(uuid);
    }
}
