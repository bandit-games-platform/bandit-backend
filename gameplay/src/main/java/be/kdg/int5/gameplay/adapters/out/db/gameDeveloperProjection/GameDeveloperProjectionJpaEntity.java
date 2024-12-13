package be.kdg.int5.gameplay.adapters.out.db.gameDeveloperProjection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(schema = "gameplay", name = "game_developer")
public class GameDeveloperProjectionJpaEntity {
    @Id
    private UUID gameId;
    private UUID developerId;
    private String gameTitle;

    public GameDeveloperProjectionJpaEntity() {
    }

    public GameDeveloperProjectionJpaEntity(UUID gameId, UUID developerId, String gameTitle) {
        this.gameId = gameId;
        this.developerId = developerId;
        this.gameTitle = gameTitle;
    }

    public UUID getGameId() {
        return gameId;
    }

    public UUID getDeveloperId() {
        return developerId;
    }

    public String getGameTitle() {
        return gameTitle;
    }
}
