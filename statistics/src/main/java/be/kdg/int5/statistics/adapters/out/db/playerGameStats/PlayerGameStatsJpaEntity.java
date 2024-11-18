package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(catalog = "statistics_db", name = "player_game_stats")
public class PlayerGameStatsJpaEntity {
    @EmbeddedId
    private PlayerGameStatsJpaId id;

    @OneToMany(mappedBy = "playerGameStatsJpaEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CompletedSessionJpaEntity> completedSessions = new HashSet<>();

    public PlayerGameStatsJpaEntity() {
    }

    public PlayerGameStatsJpaEntity(final PlayerGameStatsJpaId id, final Set<CompletedSessionJpaEntity> completedSessions) {
        this.id = id;
        this.completedSessions = completedSessions;
    }

    public PlayerGameStatsJpaId getId() {
        return id;
    }

    public void setId(PlayerGameStatsJpaId id) {
        this.id = id;
    }

    public Set<CompletedSessionJpaEntity> getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(Set<CompletedSessionJpaEntity> completedSessions) {
        this.completedSessions = completedSessions;
    }
}
