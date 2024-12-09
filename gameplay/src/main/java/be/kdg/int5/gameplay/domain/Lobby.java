package be.kdg.int5.gameplay.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Lobby {
    private final LobbyId id;
    private final GameId gameId;
    private final int maxPlayers;
    private PlayerId ownerId;
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

    public void setOwnerId(PlayerId ownerId) {
        this.ownerId = ownerId;
    }

    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public void setCurrentPlayerCount(int currentPlayerCount) {
        this.currentPlayerCount = currentPlayerCount;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public List<GameInvite> getGameInviteList() {
        return gameInviteList;
    }

    public void patch(UUID ownerId, Integer currentPlayerCount, Boolean closed) {
        if (ownerId != null) setOwnerId(new PlayerId(ownerId));
        if (currentPlayerCount != null) setCurrentPlayerCount(currentPlayerCount);
        if (closed != null) setClosed(closed);
    }
}
