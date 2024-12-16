package be.kdg.int5.gameplay.adapters.in.dto;

import java.util.UUID;

public class LobbyJoinInfoDto {
    private UUID lobbyId;
    private UUID gameId;

    public LobbyJoinInfoDto() {
    }

    public LobbyJoinInfoDto(UUID lobbyId, UUID gameId) {
        this.lobbyId = lobbyId;
        this.gameId = gameId;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
