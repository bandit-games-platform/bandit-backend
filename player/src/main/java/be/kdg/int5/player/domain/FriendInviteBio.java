package be.kdg.int5.player.domain;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

public record FriendInviteBio(
        String id,
        UUID friendInviteId,
        String username,
        String avatar,
        LocalDateTime invitedTime,
        InviteStatus status
) {
    public FriendInviteBio{
        Objects.requireNonNull(id);
        Objects.requireNonNull(friendInviteId);
        Objects.requireNonNull(username);
        Objects.requireNonNull(invitedTime);
        Objects.requireNonNull(status);
    }
}
