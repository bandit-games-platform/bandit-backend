package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.adapters.out.question.QuestionJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "conversation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "conversation_type", discriminatorType = DiscriminatorType.STRING)
public class ConversationJpaEntity {
    @Id
    private UUID id;
    private UUID userId;
    private LocalDateTime startTime;
    private LocalDateTime lastMessageTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "conversation")
    private List<QuestionJpaEntity> questions = new ArrayList<>();

    public ConversationJpaEntity() {
        this.id = UUID.randomUUID();
    }

    public ConversationJpaEntity(UUID userId, LocalDateTime startTime, LocalDateTime lastMessageTime) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.startTime = startTime;
        this.lastMessageTime = lastMessageTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public List<QuestionJpaEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionJpaEntity> questions) {
        this.questions = questions;
    }
}
