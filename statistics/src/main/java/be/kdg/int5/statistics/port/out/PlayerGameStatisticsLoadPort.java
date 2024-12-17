package be.kdg.int5.statistics.port.out;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;

import java.util.List;

public interface PlayerGameStatisticsLoadPort {
    PlayerGameStats loadPlayerGameStat(PlayerId playerId, GameId gameId);
    PlayerGameStats loadPlayerGameStatsWithAllRelationships(PlayerId playerId, GameId gameId);
    List<PlayerGameStats> loadAllPlayerGameStatsForPlayer(PlayerId playerId);
    List<PlayerGameStats> loadAllPlayerGameStatsForGame(GameId gameId);
}
