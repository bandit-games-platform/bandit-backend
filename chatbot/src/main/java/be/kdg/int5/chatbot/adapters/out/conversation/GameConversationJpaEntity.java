package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.adapters.out.question.QuestionJpaEntity;
import be.kdg.int5.chatbot.domain.GameId;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("GAME_CONVERSATION")
public class GameConversationJpaEntity extends ConversationJpaEntity {
    private UUID gameId;

    public GameConversationJpaEntity() {
    }

    public GameConversationJpaEntity(UUID gameId) {
        this.gameId = gameId;
    }

    public GameConversationJpaEntity(String userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<QuestionJpaEntity> questions, UUID gameId) {
        super(userId, startTime, lastMessageTime, questions);
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
