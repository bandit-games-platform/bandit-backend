package be.kdg.int5.statistics.port.out;
import be.kdg.int5.statistics.domain.Achievement;
import be.kdg.int5.statistics.domain.GameId;

import java.util.List;

public interface AchievementLoadPort {
    List<Achievement> loadGameAchievements(GameId gameId);
}
