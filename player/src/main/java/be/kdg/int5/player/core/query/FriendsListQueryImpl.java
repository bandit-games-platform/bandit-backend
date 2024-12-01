package be.kdg.int5.player.core.query;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.FriendsListQuery;
import be.kdg.int5.player.port.in.GetFriendsListCommand;
import be.kdg.int5.player.port.out.FriendsListLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendsListQueryImpl implements FriendsListQuery {
    private final FriendsListLoadPort friendsListLoadPort;

    public FriendsListQueryImpl(final FriendsListLoadPort friendsListLoadPort) {
        this.friendsListLoadPort = friendsListLoadPort;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Player> getFriendsList(GetFriendsListCommand command) {
        return friendsListLoadPort.getAllFriendsOfPlayer(command.playerId());
    }
}
