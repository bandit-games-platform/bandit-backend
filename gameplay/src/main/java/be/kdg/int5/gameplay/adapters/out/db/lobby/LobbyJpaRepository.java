package be.kdg.int5.gameplay.adapters.out.db.lobby;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {
    Optional<LobbyJpaEntity> findByOwnerIdAndGameId(UUID ownerId, UUID gameId);
}
