package be.kdg.int5.gameRegistry.adapters.out.db.achievement;


import be.kdg.int5.gameRegistry.adapters.out.db.game.GameJpaEntity;
import be.kdg.int5.gameRegistry.domain.AchievementId;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "achievement")
public class AchievementJpaEntity {
    @Id
    private UUID id;
    private String title;
    private int counterTotal;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game", referencedColumnName = "game_id")
    private GameJpaEntity game;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(String title, int counterTotal, String description, GameJpaEntity game) {
        this(UUID.randomUUID(), title, counterTotal, description, game);
    }

    public AchievementJpaEntity(UUID id, String title, int counterTotal, String description, GameJpaEntity game) {
        this.id = id;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
        this.game = game;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public void setCounterTotal(int counterTotal) {
        this.counterTotal = counterTotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GameJpaEntity getGame() {
        return game;
    }

    public void setGame(GameJpaEntity game) {
        this.game = game;
    }
}
