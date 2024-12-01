package be.kdg.int5.player.port.in;

import java.util.Objects;
import java.util.UUID;

public record SendFriendInviteCommand(UUID playerId, UUID friendId) {
    public SendFriendInviteCommand {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(friendId);
    }
}
