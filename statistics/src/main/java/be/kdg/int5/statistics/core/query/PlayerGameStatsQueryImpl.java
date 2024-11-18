package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerGameStatsQueryImpl implements PlayerGameStatsQuery {
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;

    public PlayerGameStatsQueryImpl(PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort) {
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
    }


    @Override
    @Transactional(readOnly = true)
    public PlayerGameStats getPlayerGameStats(final GetPlayerGameStatsCommand command) {
        return playerGameStatisticsLoadPort.loadPlayerGameStat(command.playerId(), command.gameId());
    }
}
