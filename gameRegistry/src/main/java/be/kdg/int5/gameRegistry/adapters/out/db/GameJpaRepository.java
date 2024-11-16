package be.kdg.int5.gameRegistry.adapters.out.db;

import be.kdg.int5.gameRegistry.adapters.out.db.jpaEntities.GameJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
}
