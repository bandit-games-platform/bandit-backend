package be.kdg.int5.gameplay.adapters.out.db.lobby;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "gameplay", name = "lobby")
public class LobbyJpaEntity {
    @Id
    private UUID id;
    private UUID gameId;
    private UUID ownerId;
    private int maxPlayers;
    private int currentPlayerCount;
    private boolean closed;

    public LobbyJpaEntity() {
    }

    public LobbyJpaEntity(UUID id, UUID gameId, UUID ownerId, int maxPlayers, int currentPlayerCount, boolean closed) {
        this.id = id;
        this.gameId = gameId;
        this.ownerId = ownerId;
        this.maxPlayers = maxPlayers;
        this.currentPlayerCount = currentPlayerCount;
        this.closed = closed;
    }

    public UUID getId() {
        return id;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public boolean isClosed() {
        return closed;
    }
}
