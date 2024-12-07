package be.kdg.int5.player.port.out.friends;

import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.FriendInviteBio;
import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.PlayerId;

import java.util.List;

public interface FriendInviteLoadPort {
    List<FriendInviteBio> loadAllReceivedPendingFriendInvitesForPlayer(PlayerId playerId);
    List<FriendInviteBio> loadAllSentPendingFriendInvitesForPlayer(PlayerId playerId);
    FriendInvite loadFriendInviteById(FriendInviteId friendInviteId);
}
