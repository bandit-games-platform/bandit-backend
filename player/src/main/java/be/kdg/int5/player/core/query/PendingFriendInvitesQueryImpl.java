package be.kdg.int5.player.core.query;

import be.kdg.int5.player.domain.FriendInviteBio;
import be.kdg.int5.player.port.in.query.GetPendingReceivedFriendInvitesCommand;
import be.kdg.int5.player.port.in.query.GetPendingSentFriendInvitesCommand;
import be.kdg.int5.player.port.in.query.PendingFriendInvitesQuery;
import be.kdg.int5.player.port.out.FriendInviteStatusLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PendingFriendInvitesQueryImpl implements PendingFriendInvitesQuery {
    private final FriendInviteStatusLoadPort friendInviteStatusLoadPort;

    public PendingFriendInvitesQueryImpl(final FriendInviteStatusLoadPort friendInviteStatusLoadPort) {
        this.friendInviteStatusLoadPort = friendInviteStatusLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendInviteBio> getAllReceivedPendingFriendInvites(GetPendingReceivedFriendInvitesCommand command) {
        return friendInviteStatusLoadPort.loadAllReceivedPendingFriendInvitesForPlayer(command.playerId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FriendInviteBio> getAllSentPendingFriendInvites(GetPendingSentFriendInvitesCommand command) {
        return friendInviteStatusLoadPort.loadAllSentPendingFriendInvitesForPlayer(command.playerId());
    }
}