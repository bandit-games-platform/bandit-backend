package be.kdg.int5.player.domain;

public class FriendInvite {
    private final PlayerId inviter;
    private final PlayerId invited;
    private InviteStatus status;

    public FriendInvite(final PlayerId inviter, final PlayerId invited) {
        this.inviter = inviter;
        this.invited = invited;
        this.status = InviteStatus.PENDING;
    }
}
