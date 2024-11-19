package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(schema = "statistics", name = "game_achievements")
public class AchievementJpaEntity {

    @Id
    @Column(name = "achievement_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID achievementId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column
    private int counterTotal;

    @Column(columnDefinition = "varchar(255)")
    private String title;

    @Column(columnDefinition = "varchar(1000)")
    private String description;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(final UUID achievementId,
                                final UUID gameId,
                                final int counterTotal,
                                final String title,
                                final String description) {
        this.achievementId = achievementId;
        this.gameId = gameId;
        this.counterTotal = counterTotal;
        this.title = title;
        this.description = description;
    }


    public UUID getAchievementId() {
        return achievementId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
