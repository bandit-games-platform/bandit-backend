package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.Player;

public interface PlayerUpdatePort {
    void updatePlayerDisplayName(Player player);
}
