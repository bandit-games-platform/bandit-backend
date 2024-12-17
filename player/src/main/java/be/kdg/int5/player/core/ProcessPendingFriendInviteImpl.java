package be.kdg.int5.player.core;

import be.kdg.int5.common.exceptions.UnauthorizedPlayerException;
import be.kdg.int5.player.domain.*;
import be.kdg.int5.player.port.in.ProcessPendingFriendInvite;
import be.kdg.int5.player.port.in.ProcessPendingFriendInviteCommand;
import be.kdg.int5.player.port.out.friends.FriendInviteLoadPort;
import be.kdg.int5.player.port.out.friends.FriendInviteUpdatePort;
import be.kdg.int5.player.port.out.friends.FriendRelationCreatePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProcessPendingFriendInviteImpl implements ProcessPendingFriendInvite {
    private final FriendInviteUpdatePort friendInviteUpdatePort;
    private final FriendInviteLoadPort friendInviteLoadPort;
    private final FriendRelationCreatePort friendRelationCreatePort;

    public ProcessPendingFriendInviteImpl(
            FriendInviteUpdatePort friendInviteUpdatePort,
            FriendInviteLoadPort friendInviteLoadPort,
            FriendRelationCreatePort friendRelationCreatePort) {
        this.friendInviteUpdatePort = friendInviteUpdatePort;
        this.friendInviteLoadPort = friendInviteLoadPort;
        this.friendRelationCreatePort = friendRelationCreatePort;
    }

    @Override
    @Transactional
    public boolean acceptPendingFriendInvite(ProcessPendingFriendInviteCommand command) {
        PlayerId playerId = command.playerId();
        FriendInviteId friendInviteId = command.friendInviteId();
        FriendInvite friendInvite = friendInviteLoadPort.loadFriendInviteById(friendInviteId);
        if (!friendInvite.getInvited().uuid().equals(playerId.uuid())) throw new UnauthorizedPlayerException();

        friendInvite.accept();
        friendInviteUpdatePort.updateFriendInvite(friendInvite);
        Player invitingPlayer = new Player(new PlayerId(friendInvite.getInviter().uuid()));
        Player newFriend = new Player(new PlayerId(friendInvite.getInvited().uuid()));
        FriendRelation friendRelation = invitingPlayer.addFriend(newFriend);
        friendRelationCreatePort.createFriendRelation(friendRelation);
        return true;
    }

    @Override
    @Transactional
    public boolean rejectPendingFriendInvite(ProcessPendingFriendInviteCommand command) {
        PlayerId playerId = command.playerId();
        FriendInviteId friendInviteId = command.friendInviteId();
        FriendInvite friendInvite = friendInviteLoadPort.loadFriendInviteById(friendInviteId);
        if (!friendInvite.getInvited().uuid().equals(playerId.uuid())) throw new UnauthorizedPlayerException();

        friendInvite.reject();
        friendInviteUpdatePort.updateFriendInvite(friendInvite);
        return true;
    }
}
