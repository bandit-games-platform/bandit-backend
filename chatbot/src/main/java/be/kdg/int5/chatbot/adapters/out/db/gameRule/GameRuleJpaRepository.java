package be.kdg.int5.chatbot.adapters.out.db.gameRule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRuleJpaRepository extends JpaRepository<GameRuleJpaEntity, UUID> {
}
