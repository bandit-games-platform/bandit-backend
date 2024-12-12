package be.kdg.int5.statistics.port.out;

import be.kdg.int5.statistics.domain.AchievementProgress;
import be.kdg.int5.statistics.domain.GameId;

import java.util.List;

public interface AchievementProgressForGameLoadPort {

    List<AchievementProgress> loadAllAchievementProgressForGame(GameId gameId);
}
