package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.Player;

import java.util.UUID;

public interface LoadPlayerPort {
    Player loadPlayerById(UUID playerId);
}
