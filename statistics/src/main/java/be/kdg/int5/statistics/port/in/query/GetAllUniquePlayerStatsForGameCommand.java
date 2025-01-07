package be.kdg.int5.statistics.port.in.query;

import be.kdg.int5.statistics.domain.GameId;

import static java.util.Objects.requireNonNull;

public record GetAllUniquePlayerStatsForGameCommand(GameId gameId) {
    public GetAllUniquePlayerStatsForGameCommand {
        requireNonNull(gameId);
    }
}
