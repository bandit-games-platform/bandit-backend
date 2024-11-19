package be.kdg.int5.chatbot.adapters.out.gameRule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRuleJpaRepository extends JpaRepository<GameRuleJpaEntity, UUID> {
}
