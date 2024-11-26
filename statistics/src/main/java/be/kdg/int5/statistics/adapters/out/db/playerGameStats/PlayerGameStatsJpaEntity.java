package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "statistics", name = "player_game_stats")
public class PlayerGameStatsJpaEntity {
    @EmbeddedId
    private PlayerGameStatsJpaId id;

    @OneToMany(mappedBy = "playerGameStatsJpaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CompletedSessionJpaEntity> completedSessions;

    @OneToMany(mappedBy = "playerGameStatsJpaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AchievementProgressJpaEntity> achievementProgressJpaEntities;

    public PlayerGameStatsJpaEntity() {
    }

    public PlayerGameStatsJpaEntity(PlayerGameStatsJpaId id) {
        this.id = id;
    }

    public PlayerGameStatsJpaEntity(final PlayerGameStatsJpaId id, final Set<CompletedSessionJpaEntity> completedSessions) {
        this.id = id;
        this.completedSessions = completedSessions;
        for (CompletedSessionJpaEntity completedSession: completedSessions) completedSession.setPlayerGameStatsJpaEntity(this);
        achievementProgressJpaEntities = new HashSet<>();
    }

    public PlayerGameStatsJpaId getId() {
        return id;
    }

    public Set<CompletedSessionJpaEntity> getCompletedSessions() {
        return completedSessions;
    }

    public Set<AchievementProgressJpaEntity> getAchievementProgressJpaEntities() {
        return achievementProgressJpaEntities;
    }

    public void setAchievementProgressJpaEntities(Set<AchievementProgressJpaEntity> achievementProgressJpaEntities) {
        achievementProgressJpaEntities.forEach(achievementProgressJpaEntity -> achievementProgressJpaEntity.setPlayerGameStatsJpaEntity(this));
        this.achievementProgressJpaEntities = achievementProgressJpaEntities;
    }
}
