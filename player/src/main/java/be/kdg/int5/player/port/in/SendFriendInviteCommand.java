package be.kdg.int5.player.port.in;

import be.kdg.int5.player.domain.PlayerId;
import java.util.Objects;

public record SendFriendInviteCommand(PlayerId playerId, PlayerId friendId) {
    public SendFriendInviteCommand {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(friendId);
    }
}
