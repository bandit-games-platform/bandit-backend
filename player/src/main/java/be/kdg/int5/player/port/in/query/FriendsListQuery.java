package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.port.in.query.GetFriendsListCommand;

import java.util.List;

public interface FriendsListQuery {
    List<Player> getFriendsList(GetFriendsListCommand command);
}
