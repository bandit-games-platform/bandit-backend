package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.Achievement;
import be.kdg.int5.statistics.domain.PlayerGameStats;

import java.util.List;

public interface PlayerGameStatsQuery {
    PlayerGameStats getPlayerGameStats(GetPlayerGameStatsCommand command);

    List<Achievement> getGameAchievement(GetGameAchievementCommand command);
}
