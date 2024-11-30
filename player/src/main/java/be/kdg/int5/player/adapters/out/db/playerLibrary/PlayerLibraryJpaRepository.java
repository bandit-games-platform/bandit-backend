package be.kdg.int5.player.adapters.out.db.playerLibrary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PlayerLibraryJpaRepository extends JpaRepository<PlayerLibraryJpaEntity, UUID> {
    @Query("""
    SELECT library FROm PlayerLibraryJpaEntity library
    LEFT JOIN FETCH library.playerLibraryItems
    WHERE library.playerId = :playerId
    """)
    PlayerLibraryJpaEntity findByPlayerIdWithLibraryItems(UUID playerId);
}
