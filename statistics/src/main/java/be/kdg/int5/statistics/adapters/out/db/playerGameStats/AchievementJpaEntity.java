package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(schema = "statistics", name = "game_achievements")
public class AchievementJpaEntity {

    @Id
    @Column(name = "achievement_id", columnDefinition = "varchar(36)", nullable = false, unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID achievementId;

    @Column(name = "game_id", nullable = false)
    private UUID gameId;

    @Column
    private int counterTotal;

    public AchievementJpaEntity() {
    }

    public AchievementJpaEntity(final UUID achievementId, final UUID gameId, final int counterTotal) {
        this.achievementId = achievementId;
        this.gameId = gameId;
        this.counterTotal = counterTotal;
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
}
