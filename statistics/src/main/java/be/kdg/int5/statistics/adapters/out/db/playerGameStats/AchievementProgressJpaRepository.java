package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AchievementProgressJpaRepository extends JpaRepository<AchievementProgressJpaEntity, UUID> {

    Optional<AchievementProgressJpaEntity> findAchievementProgressJpaEntitiesByAchievementIdAndPlayerGameStatsJpaEntity_IdPlayerId(
            @Param("achievementId") UUID achievementId,
            @Param("playerId") UUID playerId)
            ;

}
