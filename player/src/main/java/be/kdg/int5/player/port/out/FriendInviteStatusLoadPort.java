package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.FriendInviteBio;
import be.kdg.int5.player.domain.PlayerId;

import java.util.List;

public interface FriendInviteStatusLoadPort {
    List<FriendInviteBio> loadAllReceivedPendingFriendInvitesForPlayer(PlayerId playerId);
    List<FriendInviteBio> loadAllSentPendingFriendInvitesForPlayer(PlayerId playerId);
}
