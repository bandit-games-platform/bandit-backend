package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(schema = "statistics", name = "player_achievements_progress")
public class AchievementProgressJpaEntity {

    @Id
    @Column(name = "achievement_progress_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID achievementProgressId;

    @Column(name = "achievement_id")
    private UUID achievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false),
            @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    })
    private PlayerGameStatsJpaEntity playerGameStatsJpaEntity;

    @Column
    private int counterTotal;

    public AchievementProgressJpaEntity() {
    }

    public AchievementProgressJpaEntity(UUID achievementProgressId, UUID achievementId, PlayerGameStatsJpaEntity playerGameStatsJpaEntity, int counterTotal) {
        this.achievementProgressId = achievementProgressId;
        this.achievementId = achievementId;
        this.playerGameStatsJpaEntity = playerGameStatsJpaEntity;
        this.counterTotal = counterTotal;
    }

    public UUID getAchievementProgressId() {
        return achievementProgressId;
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public PlayerGameStatsJpaEntity getPlayerGameStatsJpaEntity() {
        return playerGameStatsJpaEntity;
    }

    public int getCounterTotal() {
        return counterTotal;
    }
}
