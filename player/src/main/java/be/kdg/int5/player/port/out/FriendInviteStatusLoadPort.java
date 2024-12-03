package be.kdg.int5.player.port.out;

import be.kdg.int5.player.adapters.in.dto.FriendInviteBioDto;
import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;

import java.util.List;

public interface FriendInviteStatusLoadPort {
    FriendInvite loadFriendInviteStatus(FriendInviteId friendInviteId);
    List<FriendInviteBioDto> loadAllPendingFriendInvitesForPlayer(PlayerId playerId);
    List<FriendInviteBioDto> loadAllSentPendingFriendInvitesForPlayer(PlayerId playerId);
}
