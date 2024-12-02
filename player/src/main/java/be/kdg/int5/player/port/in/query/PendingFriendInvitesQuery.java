package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.adapters.in.dto.FriendInviteBioDto;
import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.Player;

import java.util.List;

public interface PendingFriendInvitesQuery {
    List<FriendInviteBioDto> getAllPendingFriendInvites(GetPendingFriendInvitesCommand command);
}
