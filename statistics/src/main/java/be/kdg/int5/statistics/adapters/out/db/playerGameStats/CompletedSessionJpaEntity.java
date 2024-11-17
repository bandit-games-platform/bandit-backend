package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.GameEndState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(catalog = "statistics_db", name = "completed_game_sessions")
public class CompletedSessionJpaEntity {
    @Id
    @Column(name = "session_id", columnDefinition = "varchar(36)", nullable = false, unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sessionId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "end_state", nullable = false)
    private GameEndState endState;

    @Column(name = "turns_taken", nullable = true)
    private int turnsTaken;

    @Column(name = "avg_seconds_per_turn", nullable = true)
    private double avgSecondsPerTurn;

    @Column(name = "player_score", nullable = true)
    private int playerScore;

    @Column(name = "opponent_score", nullable = true)
    private int opponentScore;

    @Column(name = "clicks", nullable = true)
    private int clicks;

    @Column(name = "character", nullable = true)
    private String character;

    @Column(name = "was_first_to_go", nullable = true)
    private boolean wasFirstToGo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false),
            @JoinColumn(name = "game_id", referencedColumnName = "game_id", nullable = false)
    })
    private PlayerGameStatsJpaEntity playerGameStatsJpaEntity;

    public CompletedSessionJpaEntity() {
    }

    public CompletedSessionJpaEntity(
            final UUID sessionId,
            final LocalDateTime startTime,
            final LocalDateTime endTime,
            final GameEndState endState,
            final int turnsTaken,
            final double avgSecondsPerTurn,
            final int playerScore,
            final int opponentScore,
            final int clicks,
            final String character,
            final boolean wasFirstToGo,
            final PlayerGameStatsJpaEntity playerGameStatsJpaEntity) {
        this.sessionId = sessionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.endState = endState;
        this.turnsTaken = turnsTaken;
        this.avgSecondsPerTurn = avgSecondsPerTurn;
        this.playerScore = playerScore;
        this.opponentScore = opponentScore;
        this.clicks = clicks;
        this.character = character;
        this.wasFirstToGo = wasFirstToGo;
        this.playerGameStatsJpaEntity = playerGameStatsJpaEntity;
    }
}
