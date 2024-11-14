package be.kdg.int5.player.domain;

public class FriendInvite {
    private final PlayerId inviter;
    private final PlayerId invited;
    private final InviteStatus status;

    public FriendInvite(PlayerId inviter, PlayerId invited, InviteStatus status) {
        this.inviter = inviter;
        this.invited = invited;
        this.status = status;
    }
}
