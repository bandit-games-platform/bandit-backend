package be.kdg.int5.gameplay.port.in;

import java.util.Objects;
import java.util.UUID;

public record PatchLobbyCommand(
        UUID lobbyId,
        UUID ownerId,
        Integer currentPlayerCount,
        Boolean closed
) {
    public PatchLobbyCommand {
        Objects.requireNonNull(lobbyId);
    }
}
