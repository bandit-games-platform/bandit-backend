package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.GameId;

import java.util.List;

public interface AchievementsLoadPort {
    List<Achievement> loadGameAchievements(GameId gameId);
}
