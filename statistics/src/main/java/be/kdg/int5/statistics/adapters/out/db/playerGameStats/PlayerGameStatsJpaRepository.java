package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlayerGameStatsJpaRepository extends JpaRepository<PlayerGameStatsJpaEntity, PlayerGameStatsJpaId> {

    @Query("""
    SELECT stats FROM PlayerGameStatsJpaEntity stats
    LEFT JOIN FETCH stats.completedSessions
    LEFT JOIN FETCH stats.achievementProgressJpaEntities
    WHERE stats.id = :id
    """)
    PlayerGameStatsJpaEntity findByIdWithAllRelationships(PlayerGameStatsJpaId id);

    @Query("""
    SELECT stats FROM PlayerGameStatsJpaEntity stats
    WHERE stats.id.playerId = :playerId
    """)
    List<PlayerGameStatsJpaEntity> findAllByPlayerId(UUID playerId);
}
