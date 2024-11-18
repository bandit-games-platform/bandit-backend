package be.kdg.int5.statistics.adapters.out.db.playerGameStats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface AchievementProgressJpaRepository extends JpaRepository<AchievementProgressJpaEntity, UUID> {

    @Query("SELECT ap FROM AchievementProgressJpaEntity ap JOIN ap.achievement a WHERE a.achievementId = :achievementId AND ap.playerGameStatsJpaEntity.id.playerId = :playerId")
    Optional<AchievementProgressJpaEntity> findAchievementProgressByAchievementIdAndPlayerId(
            @Param("achievementId") UUID achievementId,
            @Param("playerId") UUID playerId)
            ;

}
