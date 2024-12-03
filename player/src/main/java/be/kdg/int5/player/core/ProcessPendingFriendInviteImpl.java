package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.FriendInviteId;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.ProcessPendingFriendInvite;
import be.kdg.int5.player.port.in.ProcessPendingFriendInviteCommand;
import be.kdg.int5.player.port.out.FriendInviteStatusUpdatePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessPendingFriendInviteImpl implements ProcessPendingFriendInvite {
    private final FriendInviteStatusUpdatePort friendInviteStatusUpdatePort;

    public ProcessPendingFriendInviteImpl(FriendInviteStatusUpdatePort friendInviteStatusUpdatePort) {
        this.friendInviteStatusUpdatePort = friendInviteStatusUpdatePort;
    }

    @Override
    @Transactional
    public boolean acceptPendingFriendInvite(ProcessPendingFriendInviteCommand command) {
        PlayerId playerId = command.playerId();
        FriendInviteId friendInviteId = command.friendInviteId();
        return friendInviteStatusUpdatePort.updateFriendInviteStatusToAccepted(friendInviteId, playerId);
    }

    @Override
    @Transactional
    public boolean rejectPendingFriendInvite(ProcessPendingFriendInviteCommand command) {
        PlayerId playerId = command.playerId();
        FriendInviteId friendInviteId = command.friendInviteId();
        return friendInviteStatusUpdatePort.updateFriendInviteStatusToRejected(friendInviteId, playerId);
    }
}
