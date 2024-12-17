package be.kdg.int5.gameplay.domain;

import java.util.Objects;

public record LobbyJoinInfo(LobbyId lobbyId, GameId gameId) {
    public LobbyJoinInfo {
        Objects.requireNonNull(lobbyId);
        Objects.requireNonNull(gameId);
    }
}
