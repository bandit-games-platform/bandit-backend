package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.query.PlayerAchievementProgressForPlayedGamesCommand;
import be.kdg.int5.statistics.port.in.query.PlayerAchievementProgressForPlayedGamesQuery;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerAchievementProgressForPlayedGamesQueryImpl implements PlayerAchievementProgressForPlayedGamesQuery {
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;

    public PlayerAchievementProgressForPlayedGamesQueryImpl(PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort) {
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerGameStats> getAllPlayerAchievementProgressForGamesTheyPlayed(
            PlayerAchievementProgressForPlayedGamesCommand command
    ) {
        return playerGameStatisticsLoadPort.loadAllPlayerGameStatsForPlayer(command.playerId());
    }
}
