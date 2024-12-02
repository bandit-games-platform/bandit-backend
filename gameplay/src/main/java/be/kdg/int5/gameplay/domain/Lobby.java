package be.kdg.int5.gameplay.domain;

import java.util.ArrayList;
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
        this(id, gameId, maxPlayers, ownerId, 1, false, null);
    }

    public Lobby(LobbyId id, GameId gameId, int maxPlayers, PlayerId ownerId, int currentPlayerCount, boolean closed, List<GameInvite> gameInviteList) {
        this.id = Objects.requireNonNull(id);
        this.gameId = Objects.requireNonNull(gameId);
        this.maxPlayers = maxPlayers;
        this.ownerId = Objects.requireNonNull(ownerId);
        this.currentPlayerCount = currentPlayerCount;
        this.closed = closed;
        this.gameInviteList = Objects.requireNonNullElse(gameInviteList, new ArrayList<>());
    }

    public LobbyId getId() {
        return id;
    }

    public GameId getGameId() {
        return gameId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public PlayerId getOwnerId() {
        return ownerId;
    }

    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public boolean isClosed() {
        return closed;
    }

    public List<GameInvite> getGameInviteList() {
        return gameInviteList;
    }
}
