package be.kdg.int5.gameplay.adapters.out.db.gameDeveloperProjection;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameDeveloperProjectionJpaRepository extends JpaRepository<GameDeveloperProjectionJpaEntity, UUID> {
}
