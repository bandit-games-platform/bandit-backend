package be.kdg.int5.chatbot.adapters.out.db.gameDetails;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameDetailsJpaRepository extends JpaRepository<GameDetailsJpaEntity, UUID> {
}
