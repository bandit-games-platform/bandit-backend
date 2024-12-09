package be.kdg.int5.player.port.in;

public interface ProcessPendingFriendInvite {
    boolean acceptPendingFriendInvite(ProcessPendingFriendInviteCommand command);
    boolean rejectPendingFriendInvite(ProcessPendingFriendInviteCommand command);
}
