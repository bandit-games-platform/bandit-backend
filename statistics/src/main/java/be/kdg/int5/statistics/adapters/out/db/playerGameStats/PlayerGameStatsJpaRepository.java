package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGameStatsJpaRepository extends JpaRepository<PlayerGameStatsJpaEntity, PlayerGameStatsJpaId> {
}
