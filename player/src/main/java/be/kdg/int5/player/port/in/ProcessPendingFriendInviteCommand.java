package be.kdg.int5.player.port.in;
import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.PlayerId;
import java.util.Objects;

public record ProcessPendingFriendInviteCommand(FriendInviteId friendInviteId, PlayerId playerId) {
    public ProcessPendingFriendInviteCommand{
        Objects.requireNonNull(friendInviteId);
        Objects.requireNonNull(playerId);
    }
}
