package be.kdg.int5.gameRegistry.adapters.out.db.game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {

    @Query("""
    SELECT game FROM GameJpaEntity game
    LEFT JOIN FETCH game.rules
    LEFT JOIN FETCH game.screenshots
    LEFT JOIN FETCH game.developer
    LEFT JOIN FETCH game.achievements
    WHERE game.id = :id
    """)
    GameJpaEntity findByIdWithAllRelationships(UUID id);

    @Query("""
    SELECT game FROM GameJpaEntity game
    LEFT JOIN FETCH game.developer
    WHERE game.gameId = :id
    """)
    GameJpaEntity findByIdWithDeveloper(UUID id);

    @Query("""
    SELECT game FROM GameJpaEntity game
    LEFT JOIN FETCH game.rules
    LEFT JOIN FETCH game.screenshots
    LEFT JOIN FETCH game.developer
    LEFT JOIN FETCH game.achievements
    """)
    List<GameJpaEntity> findAllWithAllRelationships();
}
