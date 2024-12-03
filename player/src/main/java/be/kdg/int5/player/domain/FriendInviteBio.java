package be.kdg.int5.player.domain;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendInviteBio(String id, UUID friendInviteId, @NotNull String username, @NotNull String avatar,
                              LocalDateTime invitedTime, InviteStatus status) {
    public FriendInviteBio(String id, UUID friendInviteId, String username, String avatar, LocalDateTime invitedTime, InviteStatus status) {
        this.id = id;
        this.friendInviteId = friendInviteId;
        this.username = username;
        this.avatar = avatar;
        this.invitedTime = invitedTime;
        this.status = status;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String avatar() {
        return avatar;
    }
}
