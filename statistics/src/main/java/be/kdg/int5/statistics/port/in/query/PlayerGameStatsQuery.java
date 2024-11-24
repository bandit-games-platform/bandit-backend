package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;

import java.util.Optional;

public interface PlayerGameStatsQuery {
    Optional<PlayerGameStats> getPlayerGameStats(GetPlayerGameStatsCommand command);
}
