package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Game;

import java.util.List;
import java.util.UUID;

public interface GamesLoadPort {
    Game loadGameByIdWithDetails(UUID gameId);
    Game loadGameById(UUID gameId);
    Game loadGameByIdWithAchievements(UUID gameId);

    List<Game> loadAllGamesWithIcon();
}
