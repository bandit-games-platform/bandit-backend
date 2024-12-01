package be.kdg.int5.player.adapters.out.db.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
    List<PlayerJpaEntity> findByDisplayNameContainingIgnoreCase(String displayName);
}
