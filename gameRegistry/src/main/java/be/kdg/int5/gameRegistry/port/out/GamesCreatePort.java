package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Game;

public interface GamesCreatePort {
    boolean create(Game newGame);
}
