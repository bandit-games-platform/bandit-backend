package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.adapters.in.dto.FriendInviteBioDto;

import java.util.List;

public interface PendingFriendInvitesQuery {
    List<FriendInviteBioDto> getAllReceivedPendingFriendInvites(GetPendingReceivedFriendInvitesCommand command);
    List<FriendInviteBioDto> getAllSentPendingFriendInvites(GetPendingSentFriendInvitesCommand command);
}
