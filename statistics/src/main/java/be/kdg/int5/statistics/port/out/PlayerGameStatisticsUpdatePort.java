package be.kdg.int5.statistics.port.out;

import be.kdg.int5.statistics.domain.PlayerGameStats;

public interface PlayerGameStatisticsUpdatePort {
    void addNewCompletedSession(PlayerGameStats playerGameStats);
    void updateAchievementProgress(PlayerGameStats playerGameStats);
}
