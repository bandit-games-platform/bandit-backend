package be.kdg.int5.chatbot.adapters.out.db.conversation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

    @Query("""
            SELECT g FROM ConversationJpaEntity g
            JOIN FETCH g.questions q
            JOIN FETCH q.answer a
            WHERE g.userId = :userId
            AND g.startTime = :startTime
            """)
    ConversationJpaEntity findByUserIdAndStartTimeWithQuestions(UUID userId, LocalDateTime startTime);

    @Query("""
    SELECT DISTINCT p FROM PlatformConversationJpaEntity p
    JOIN FETCH p.questions q
    JOIN FETCH q.answer a
    WHERE p.startTime = (
         SELECT MAX(pc.startTime) FROM PlatformConversationJpaEntity pc
         WHERE pc.userId = :userId
     )
     AND p.userId = :userId
    """)
    PlatformConversationJpaEntity findPlatformConversationByUserIdAndLatestStartTime(UUID userId);
}
