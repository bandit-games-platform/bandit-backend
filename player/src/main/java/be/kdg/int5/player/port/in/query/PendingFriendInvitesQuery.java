package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.domain.FriendInviteBio;

import java.util.List;

public interface PendingFriendInvitesQuery {
    List<FriendInviteBio> getAllReceivedPendingFriendInvites(GetPendingReceivedFriendInvitesCommand command);
    List<FriendInviteBio> getAllSentPendingFriendInvites(GetPendingSentFriendInvitesCommand command);
}
