package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import be.kdg.int5.statistics.domain.GameEndState;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "statistics", name = "completed_game_sessions")
public class CompletedSessionJpaEntity {
    @Id
    @Column(name = "session_id", columnDefinition = "varchar(36)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID sessionId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Column(name = "end_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private GameEndState endState;

    @Column(name = "turns_taken", nullable = true)
    private Integer turnsTaken;

    @Column(name = "avg_seconds_per_turn", nullable = true)
    private Double avgSecondsPerTurn;

    @Column(name = "player_score", nullable = true)
    private Integer playerScore;

    @Column(name = "opponent_score", nullable = true)
    private Integer opponentScore;

    @Column(name = "clicks", nullable = true)
    private Integer clicks;

    @Column(name = "character", nullable = true)
    private String character;

    @Column(name = "was_first_to_go", nullable = true)
    private Boolean wasFirstToGo;

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
            final Integer turnsTaken,
            final Double avgSecondsPerTurn,
            final Integer playerScore,
            final Integer opponentScore,
            final Integer clicks,
            final String character,
            final Boolean wasFirstToGo
    ) {
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
    }


    public UUID getSessionId() {
        return sessionId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public GameEndState getEndState() {
        return endState;
    }

    public Integer getTurnsTaken() {
        return turnsTaken;
    }

    public Double getAvgSecondsPerTurn() {
        return avgSecondsPerTurn;
    }

    public Integer getPlayerScore() {
        return playerScore;
    }

    public Integer getOpponentScore() {
        return opponentScore;
    }

    public Integer getClicks() {
        return clicks;
    }

    public String getCharacter() {
        return character;
    }

    public Boolean isWasFirstToGo() {
        return wasFirstToGo;
    }

    public PlayerGameStatsJpaEntity getPlayerGameStatsJpaEntity() {
        return playerGameStatsJpaEntity;
    }

    public void setPlayerGameStatsJpaEntity(PlayerGameStatsJpaEntity playerGameStatsJpaEntity) {
        this.playerGameStatsJpaEntity = playerGameStatsJpaEntity;
    }
}
