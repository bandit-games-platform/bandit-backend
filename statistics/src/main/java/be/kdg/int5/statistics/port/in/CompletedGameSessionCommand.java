package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import static java.util.Objects.requireNonNull;

public record CompletedGameSessionCommand(PlayerGameStats playerGameStats) {
    public CompletedGameSessionCommand{
        requireNonNull(playerGameStats);
    }
}
