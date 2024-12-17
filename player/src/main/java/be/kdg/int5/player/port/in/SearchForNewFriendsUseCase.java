package be.kdg.int5.player.port.in;

import be.kdg.int5.player.domain.Player;

import java.util.List;

public interface SearchForNewFriendsUseCase {
    List<Player> searchForNewFriends(SearchForNewFriendsCommand command);
}
