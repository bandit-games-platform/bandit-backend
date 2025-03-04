package be.kdg.int5.chatbot.adapters.out.db.question;

import be.kdg.int5.chatbot.adapters.out.db.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.db.conversation.ConversationJpaEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "question")
public class QuestionJpaEntity {
    @Id
    private UUID id;
    private String text;
    private LocalDateTime submittedAt;
    private Boolean isInitial;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private ConversationJpaEntity conversation;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private AnswerJpaEntity answer;

    public QuestionJpaEntity() {
        this.id = UUID.randomUUID();
    }

    public QuestionJpaEntity(
            String text,
            LocalDateTime submittedAt,
            Boolean isInitial,
            ConversationJpaEntity conversation,
            AnswerJpaEntity answer) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.submittedAt = submittedAt;
        this.isInitial = isInitial;
        this.conversation = conversation;
        this.answer = answer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Boolean getInitial() {
        return isInitial;
    }

    public void setInitial(Boolean initial) {
        isInitial = initial;
    }

    public ConversationJpaEntity getConversation() {
        return conversation;
    }

    public void setConversation(ConversationJpaEntity conversation) {
        this.conversation = conversation;
    }

    public AnswerJpaEntity getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerJpaEntity answer) {
        this.answer = answer;
    }
}
