package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.GameDeveloperProjection;
import be.kdg.int5.gameplay.domain.GameId;

public interface GameDeveloperProjectionLoadPort {
    GameDeveloperProjection loadByGameId(GameId gameId);
}
