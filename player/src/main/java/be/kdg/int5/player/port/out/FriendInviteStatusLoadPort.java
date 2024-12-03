package be.kdg.int5.player.port.out;

import be.kdg.int5.player.adapters.in.dto.FriendInviteBioDto;
import be.kdg.int5.player.domain.PlayerId;

import java.util.List;

public interface FriendInviteStatusLoadPort {
    List<FriendInviteBioDto> loadAllReceivedPendingFriendInvitesForPlayer(PlayerId playerId);
    List<FriendInviteBioDto> loadAllSentPendingFriendInvitesForPlayer(PlayerId playerId);
}
