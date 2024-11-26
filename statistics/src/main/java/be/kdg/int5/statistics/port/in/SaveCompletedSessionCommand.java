package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.GameEndState;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;

import java.time.LocalDateTime;
import java.util.Objects;

public record SaveCompletedSessionCommand(
        PlayerId playerId,
        GameId gameId,

        LocalDateTime startTime,
        LocalDateTime endTime,
        GameEndState endState,
        Integer turnsTaken,
        Double avgSecondsPerTurn,
        Integer playerScore,
        Integer opponentScore,
        Integer clicks,
        String character,
        Boolean wasFirstToGo
) {
    public SaveCompletedSessionCommand {
        Objects.requireNonNull(playerId);
        Objects.requireNonNull(gameId);

        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        Objects.requireNonNull(endState);
    }
}
