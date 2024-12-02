package be.kdg.int5.gameplay.adapters.out.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {
    @Query("""
    SELECT lobby FROM LobbyJpaEntity lobby
    LEFT JOIN FETCH lobby.gameInvites
    WHERE lobby.id = :id
    """)
    Optional<LobbyJpaEntity> findByIdWithInvites(UUID id);
}
