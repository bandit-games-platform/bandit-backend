package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.adapters.out.question.QuestionJpaEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("PLATFORM_CONVERSATION")
public class PlatformConversationJpaEntity extends ConversationJpaEntity {
    private String currentPage;

    public PlatformConversationJpaEntity() {
    }

    public PlatformConversationJpaEntity(String currentPage) {
        this.currentPage = currentPage;
    }

    public PlatformConversationJpaEntity(String userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<QuestionJpaEntity> questions, String currentPage) {
        super(userId, startTime, lastMessageTime, questions);
        this.currentPage = currentPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
