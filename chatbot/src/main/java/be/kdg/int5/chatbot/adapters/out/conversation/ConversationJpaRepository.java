package be.kdg.int5.chatbot.adapters.out.conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ConversationJpaRepository extends JpaRepository<ConversationJpaEntity, UUID> {

    @Query("""
            SELECT g FROM GameConversationJpaEntity g
            JOIN FETCH g.questions q
            JOIN FETCH q.answer a
            WHERE g.userId = :userId
            AND g.gameId = :gameId
            """)
    GameConversationJpaEntity findByUserIdAndGameIdWithQuestions(UUID userId, UUID gameId);
}
