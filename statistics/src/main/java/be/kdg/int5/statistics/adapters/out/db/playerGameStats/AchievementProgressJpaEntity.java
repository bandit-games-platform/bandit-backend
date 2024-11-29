package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "statistics", name = "player_achievements_progress")
public class AchievementProgressJpaEntity {

    @Id
    @Column(name = "achievement_progress_id")
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

    public AchievementProgressJpaEntity(UUID achievementId, int counterTotal) {
        this.achievementProgressId = UUID.randomUUID();
        this.achievementId = achievementId;
        this.counterTotal = counterTotal;
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

    public void setPlayerGameStatsJpaEntity(PlayerGameStatsJpaEntity playerGameStatsJpaEntity) {
        this.playerGameStatsJpaEntity = playerGameStatsJpaEntity;
    }

    public int getCounterTotal() {
        return counterTotal;
    }

    public void setCounterTotal(int counterTotal) {
        this.counterTotal = counterTotal;
    }
}
