package be.kdg.int5.gameplay.domain;

import java.util.Objects;
import java.util.UUID;

public record GameInviteId(UUID uuid) {
    public GameInviteId {
        Objects.requireNonNull(uuid);
    }
}
