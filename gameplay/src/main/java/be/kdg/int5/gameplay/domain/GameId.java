package be.kdg.int5.gameplay.domain;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record GameId(UUID uuid) {
    public GameId {
        requireNonNull(uuid);
    }
}
