package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.Player;

import java.util.List;
import java.util.UUID;

public interface PlayerLoadPort {
    Player loadPlayerById(UUID playerId);
    Player loadPlayerBioById(UUID playerId);
    Player loadPlayerByIdWithoutJoinDate(UUID playerId);
}
