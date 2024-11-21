package be.kdg.int5.gameRegistry.adapters.out.db.achievement;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "achievement")
public class AchievementJpaEntity {
    @Id
    private UUID id;
    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    private String title;
    private int counterTotal;
    private String description;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(String title, int counterTotal, String description, UUID gameId) {
        this(UUID.randomUUID(), title, counterTotal, description, gameId);
    }

    public AchievementJpaEntity(UUID id, String title, int counterTotal, String description, UUID gameId) {
        this.id = id;
        this.title = title;
        this.counterTotal = counterTotal;
        this.description = description;
        this.gameId = gameId;
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

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
