package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.Achievement;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.query.GetGameAchievementCommand;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;
import be.kdg.int5.statistics.port.out.AchievementLoadPort;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerGameStatsQueryImpl implements PlayerGameStatsQuery {
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;
    private final AchievementLoadPort achievementLoadPort;

    public PlayerGameStatsQueryImpl(PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort, AchievementLoadPort achievementLoadPort) {
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
        this.achievementLoadPort = achievementLoadPort;
    }


    @Override
    @Transactional(readOnly = true)
    public PlayerGameStats getPlayerGameStats(final GetPlayerGameStatsCommand command) {
        return playerGameStatisticsLoadPort.loadPlayerGameStat(command.playerId(), command.gameId());
    }

    @Override
    public List<Achievement> getGameAchievement(GetGameAchievementCommand command) {
        return achievementLoadPort.loadGameAchievements(command.gameId());
    }
}
