package be.kdg.int5.chatbot.adapters.out.db.gameDetails;

import be.kdg.int5.chatbot.adapters.out.db.gameRule.GameRuleJpaEntity;
import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "game_details")
public class GameDetailsJpaEntity {
    @Id
    @Column(name = "game_id")
    private UUID gameId;
    private String title;
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gameDetails")
    private Set<GameRuleJpaEntity> gameRules;

    public GameDetailsJpaEntity() {
    }

    public GameDetailsJpaEntity(UUID gameId, String title, String description, Set<GameRuleJpaEntity> gameRules) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.gameRules = gameRules;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GameRuleJpaEntity> getGameRules() {
        return gameRules;
    }

    public void setGameRules(Set<GameRuleJpaEntity> gameRules) {
        this.gameRules = gameRules;
    }
}
