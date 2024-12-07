package be.kdg.int5.gameplay.adapters.out.db;

import jakarta.persistence.*;

import java.util.List;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lobby_id")
    private List<GameInviteJpaEntity> gameInvites;

    public LobbyJpaEntity() {
    }

    public LobbyJpaEntity(UUID id, UUID gameId, UUID ownerId, int maxPlayers, int currentPlayerCount, boolean closed, List<GameInviteJpaEntity> gameInvites) {
        this.id = id;
        this.gameId = gameId;
        this.ownerId = ownerId;
        this.maxPlayers = maxPlayers;
        this.currentPlayerCount = currentPlayerCount;
        this.closed = closed;
        this.gameInvites = gameInvites;
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

    public List<GameInviteJpaEntity> getGameInvites() {
        return gameInvites;
    }
}
