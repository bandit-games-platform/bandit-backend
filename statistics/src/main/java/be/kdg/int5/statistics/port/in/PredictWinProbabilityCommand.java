package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;

import static java.util.Objects.requireNonNull;

public record PredictWinProbabilityCommand(GameId gameId, PlayerId player1, PlayerId player2) {
    public PredictWinProbabilityCommand {
        requireNonNull(gameId);
        requireNonNull(player1);
        requireNonNull(player2);
    }
}
