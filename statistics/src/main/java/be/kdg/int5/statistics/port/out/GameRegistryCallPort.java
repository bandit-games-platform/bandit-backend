package be.kdg.int5.statistics.port.out;

import be.kdg.int5.statistics.domain.GameId;

import java.util.UUID;

public interface GameRegistryCallPort {
    boolean doesDeveloperOwnGame(UUID developerId, GameId gameId);
}
