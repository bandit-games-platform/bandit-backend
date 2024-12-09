package be.kdg.int5.player.domain;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record FriendInviteId(UUID uuid) {
    public FriendInviteId {
        requireNonNull(uuid);
    }
}
