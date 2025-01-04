package be.kdg.int5.chatbot.adapters.out.db.gameDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface GameDetailsJpaRepository extends JpaRepository<GameDetailsJpaEntity, UUID> {
    @Query("""
    SELECT game FROM GameDetailsJpaEntity game
    JOIN FETCH game.gameRules
    WHERE game.gameId = :gameId
    """)
    GameDetailsJpaEntity findByIdWithRelationships(UUID gameId);
}
