package be.kdg.int5.gameplay.port.in;

import java.util.Objects;
import java.util.UUID;

public record CreateLobbyCommand(UUID gameId, UUID ownerId, int maxPlayers) {
    public CreateLobbyCommand {
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(ownerId);
        if (maxPlayers < 2) throw new IllegalArgumentException("A lobby only should be created for at least 2 players.");
    }
}
