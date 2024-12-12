package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.AchievementProgress;
import be.kdg.int5.statistics.domain.CompletedSession;

import java.util.List;

public interface StatisticsForGameQuery {
    List<CompletedSession> getAllCompletedSessionsForGame(GetStatisticsForGameCommand command);

    List<AchievementProgress> getAllAchievementProgressForGame(GetStatisticsForGameCommand command);
}
