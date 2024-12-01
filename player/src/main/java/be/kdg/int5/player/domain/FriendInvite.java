package be.kdg.int5.player.domain;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class FriendInvite {
    private final FriendInviteId id;
    private final PlayerId inviter;
    private final PlayerId invited;
    private InviteStatus status;
    private final LocalDateTime invitedTime;

    public FriendInvite(FriendInviteId id, final PlayerId inviter, final PlayerId invited, InviteStatus status, LocalDateTime invitedTime) {
        requireNonNull(id);
        requireNonNull(inviter);
        requireNonNull(invited);

        this.id = id;
        this.inviter = inviter;
        this.invited = invited;
        this.status = status;
        this.invitedTime = invitedTime;

    }

    public FriendInviteId getId() {
        return id;
    }

    public PlayerId getInviter() {
        return inviter;
    }

    public PlayerId getInvited() {
        return invited;
    }

    public InviteStatus getStatus() {
        return status;
    }

    public LocalDateTime getInvitedTime() {
        return invitedTime;
    }

    public void setStatusToAccepted() {
        this.status = InviteStatus.ACCEPTED;
    }

    public void setStatusToRejected() {
        this.status = InviteStatus.REJECTED;
    }
}