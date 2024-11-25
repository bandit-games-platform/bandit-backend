package be.kdg.int5.statistics.adapters.out.db.playerGameStats;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class PlayerGameStatsJpaId implements Serializable {
    @Column(name = "player_id", nullable = false)
    private UUID playerId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    protected PlayerGameStatsJpaId() {
    }

    public PlayerGameStatsJpaId(final UUID playerId, final UUID gameId) {
        this.playerId = playerId;
        this.gameId = gameId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerGameStatsJpaId that)) return false;
        return Objects.equals(playerId, that.playerId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, gameId);
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
