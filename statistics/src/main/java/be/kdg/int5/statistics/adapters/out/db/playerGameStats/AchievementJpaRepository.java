package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, UUID> {
    Optional<List<AchievementJpaEntity>> findAllByGameId (@Param("gameId") UUID gameId);
}
