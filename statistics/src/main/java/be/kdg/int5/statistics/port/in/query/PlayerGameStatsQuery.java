package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;

public interface PlayerGameStatsQuery {
    PlayerGameStats getPlayerGameStats(GetPlayerGameStatsCommand command);
}
