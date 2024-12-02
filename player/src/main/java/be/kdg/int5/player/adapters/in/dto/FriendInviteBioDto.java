package be.kdg.int5.player.adapters.in.dto;
import be.kdg.int5.player.domain.InviteStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class FriendInviteBioDto {
    private final String id;
    @NotNull
    private final String username;
    @NotNull
    private final String avatar;
    private final LocalDateTime invitedTime;
    private final InviteStatus status;

    public FriendInviteBioDto(String id, String username, String avatar, LocalDateTime invitedTime, InviteStatus status) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.invitedTime = invitedTime;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public LocalDateTime getInvitedTime() {
        return invitedTime;
    }
}
