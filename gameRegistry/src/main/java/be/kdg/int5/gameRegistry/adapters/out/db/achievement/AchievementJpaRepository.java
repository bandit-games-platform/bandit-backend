package be.kdg.int5.gameRegistry.adapters.out.db.achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, UUID> {

}
