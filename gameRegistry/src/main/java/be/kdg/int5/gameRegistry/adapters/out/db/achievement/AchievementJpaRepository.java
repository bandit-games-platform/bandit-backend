package be.kdg.int5.gameRegistry.adapters.out.db.achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, UUID> {
    Optional<List<AchievementJpaEntity>> findAllByGameId (@Param("gameId") UUID gameId);
}
