package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Game;

public interface GamesUpdatePort {
    boolean update(Game game);
}
