package be.kdg.int5.player.domain;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class FriendInvite {
    private final FriendInviteId id;
    private final PlayerId inviter;
    private final PlayerId invited;
    private InviteStatus status;
    private final LocalDateTime invitedTime;

    public FriendInvite(FriendInviteId id, final PlayerId inviter, final PlayerId invited, LocalDateTime invitedTime) {
        requireNonNull(id);
        requireNonNull(inviter);
        requireNonNull(invited);

        this.id = id;
        this.inviter = inviter;
        this.invited = invited;
        this.status = InviteStatus.PENDING;
        this.invitedTime = invitedTime;

    }
    public void accept() {
        this.status = InviteStatus.ACCEPTED;
    }

    public void reject() {
        this.status = InviteStatus.REJECTED;
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

    public void setStatus(InviteStatus status) {
        this.status = status;
    }
}