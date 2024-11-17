package be.kdg.int5.gameplay.domain;

import java.util.List;
import java.util.Objects;

public class Lobby {
    private final LobbyId id;
    private final GameId gameId;
    private int maxPlayers;
    private final PlayerId ownerId;
    private int currentPlayerCount;
    private boolean closed;
    private List<GameInvite> gameInviteList;

    public Lobby(LobbyId id, GameId gameId, int maxPlayers, PlayerId ownerId) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(gameId);
        Objects.requireNonNull(ownerId);

        this.id = id;
        this.gameId = gameId;
        this.maxPlayers = maxPlayers;
        this.ownerId = ownerId;
    }
}
