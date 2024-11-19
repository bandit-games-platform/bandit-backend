package be.kdg.int5.gameRegistry.adapters.out.db.game;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeveloperJpaRepository extends JpaRepository<DeveloperJpaEntity, UUID> {
}
