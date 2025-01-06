package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.query.GetAllUniquePlayerStatsForGameCommand;
import be.kdg.int5.statistics.port.in.query.GetUniquePlayerStatsForGameQuery;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GetUniquePlayerStatsForGameQueryImpl implements GetUniquePlayerStatsForGameQuery {
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;

    public GetUniquePlayerStatsForGameQueryImpl(PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort) {
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
    }

    @Override
    public Set<PlayerGameStats> getAllUniquePlayerStatsForGame(GetAllUniquePlayerStatsForGameCommand command) {
        return playerGameStatisticsLoadPort.loadAllUniquePlayerGameStatsForGame(command.gameId());
    }
}
