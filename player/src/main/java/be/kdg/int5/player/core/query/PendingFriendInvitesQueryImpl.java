package be.kdg.int5.player.core.query;

import be.kdg.int5.player.domain.FriendInviteBio;
import be.kdg.int5.player.port.in.query.GetPendingReceivedFriendInvitesCommand;
import be.kdg.int5.player.port.in.query.GetPendingSentFriendInvitesCommand;
import be.kdg.int5.player.port.in.query.PendingFriendInvitesQuery;
import be.kdg.int5.player.port.out.friends.FriendInviteLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PendingFriendInvitesQueryImpl implements PendingFriendInvitesQuery {
    private final FriendInviteLoadPort friendInviteLoadPort;

    public PendingFriendInvitesQueryImpl(final FriendInviteLoadPort friendInviteLoadPort) {
        this.friendInviteLoadPort = friendInviteLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendInviteBio> getAllReceivedPendingFriendInvites(GetPendingReceivedFriendInvitesCommand command) {
        return friendInviteLoadPort.loadAllReceivedPendingFriendInvitesForPlayer(command.playerId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendInviteBio> getAllSentPendingFriendInvites(GetPendingSentFriendInvitesCommand command) {
        return friendInviteLoadPort.loadAllSentPendingFriendInvitesForPlayer(command.playerId());
    }
}