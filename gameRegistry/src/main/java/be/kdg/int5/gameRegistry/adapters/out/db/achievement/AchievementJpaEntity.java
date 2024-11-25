package be.kdg.int5.gameRegistry.adapters.out.db.achievement;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "achievement")
public class AchievementJpaEntity {
    @Id
    private UUID id;
    @Column(name = "game_id")
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

    public int getCounterTotal() {
        return counterTotal;
    }

    public String getDescription() {
        return description;
    }

    public UUID getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AchievementJpaEntity that)) return false;
        return getCounterTotal() == that.getCounterTotal() && Objects.equals(getId(), that.getId()) && Objects.equals(getGameId(), that.getGameId()) && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGameId(), getTitle(), getCounterTotal(), getDescription());
    }
}
