package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.FriendInvite;
import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.SendFriendInviteCommand;
import be.kdg.int5.player.port.in.SendFriendInviteUseCase;
import be.kdg.int5.player.port.out.friends.FriendInviteCreatePort;
import be.kdg.int5.player.port.out.PlayerLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SendFriendInviteUseCaseImpl implements SendFriendInviteUseCase {
    private final FriendInviteCreatePort friendInviteCreatePort;
    private final PlayerLoadPort playerLoadPort;

    public SendFriendInviteUseCaseImpl(FriendInviteCreatePort friendInviteCreatePort,
                                       PlayerLoadPort playerLoadPort) {
        this.friendInviteCreatePort = friendInviteCreatePort;
        this.playerLoadPort = playerLoadPort;
    }

    @Override
    @Transactional
    public boolean sendFriendInvite(SendFriendInviteCommand command) {
        PlayerId playerId = command.playerId();
        PlayerId friendId = command.friendId();

        Player player = playerLoadPort.loadPlayerByIdWithoutJoinDate(playerId.uuid());
        FriendInvite newFriendInvite = player.sendFriendInvite(friendId);
        FriendInvite created = friendInviteCreatePort.createFriendInviteStatus(newFriendInvite);
        return created != null;
    }
}