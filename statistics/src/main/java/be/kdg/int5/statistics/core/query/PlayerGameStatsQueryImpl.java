package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;

public class PlayerGameStatsQueryImpl implements PlayerGameStatsQuery {

    @Override
    public PlayerGameStats getPlayerGameStats(GetPlayerGameStatsCommand command) {
        return null;
    }
}
