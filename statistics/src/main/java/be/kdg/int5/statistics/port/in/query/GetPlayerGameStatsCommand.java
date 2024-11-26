package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import static java.util.Objects.requireNonNull;

public record GetPlayerGameStatsCommand(PlayerId playerId, GameId gameId) {
    public GetPlayerGameStatsCommand {
        requireNonNull(playerId);
        requireNonNull(gameId);
    }
}
