package be.kdg.int5.chatbot.adapters.out.conversation;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
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

    public GameConversationJpaEntity(UUID userId, LocalDateTime startTime, LocalDateTime lastMessageTime, UUID gameId) {
        super(userId, startTime, lastMessageTime);
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
