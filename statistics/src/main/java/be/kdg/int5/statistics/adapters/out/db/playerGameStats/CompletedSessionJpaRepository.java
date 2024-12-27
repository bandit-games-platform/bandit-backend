package be.kdg.int5.statistics.adapters.out.db.playerGameStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CompletedSessionJpaRepository extends JpaRepository<CompletedSessionJpaEntity, UUID> {
    @Query("""
    SELECT session FROM CompletedSessionJpaEntity session
    WHERE session.playerGameStatsJpaEntity.id.playerId = :playerId
    """)
    List<CompletedSessionJpaEntity> findAllByPlayerId(UUID playerId);

    @Query("""
    SELECT session FROM CompletedSessionJpaEntity session
    WHERE session.playerGameStatsJpaEntity.id.playerId = :playerId
    AND session.playerGameStatsJpaEntity.id.gameId = :gameId
    """)
    List<CompletedSessionJpaEntity> findAllByGameIdAndPlayerId(UUID gameId, UUID playerId);
}
