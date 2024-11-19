package be.kdg.int5.chatbot.adapters.out.gameDetails;

import be.kdg.int5.chatbot.adapters.out.gameRule.GameRuleJpaEntity;
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
}
