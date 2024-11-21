package be.kdg.int5.chatbot.adapters.out.conversation;

import be.kdg.int5.chatbot.adapters.out.question.QuestionJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "conversation")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "conversation_type", discriminatorType = DiscriminatorType.STRING)
public class ConversationJpaEntity {
    @Id
    private UUID id;
    private String userId;
    private LocalDateTime startTime;
    private LocalDateTime lastMessageTime;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "conversation")
    private List<QuestionJpaEntity> questions;

    public ConversationJpaEntity() {
    }

    public ConversationJpaEntity(String userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<QuestionJpaEntity> questions) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.startTime = startTime;
        this.lastMessageTime = lastMessageTime;
        this.questions = questions;
    }
}
