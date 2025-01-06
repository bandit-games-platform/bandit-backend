package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.PlayerGameStats;

import java.util.Set;

public interface GetUniquePlayerStatsForGameQuery {
    Set<PlayerGameStats> getAllUniquePlayerStatsForGame(
            GetAllUniquePlayerStatsForGameCommand command
    );
}
