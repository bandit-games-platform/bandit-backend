package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;

import java.util.List;

public interface PlayerUsernameLoadPort {
    List<Player> loadSearchPlayersByUsernameExcludingFriends(String username, PlayerId playerId);
}
