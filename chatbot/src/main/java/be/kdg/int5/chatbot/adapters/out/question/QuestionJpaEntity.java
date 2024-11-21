package be.kdg.int5.chatbot.adapters.out.question;

import be.kdg.int5.chatbot.adapters.out.answer.AnswerJpaEntity;
import be.kdg.int5.chatbot.adapters.out.conversation.ConversationJpaEntity;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "question")
public class QuestionJpaEntity {
    @Id
    private UUID id;
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationJpaEntity conversation;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private AnswerJpaEntity answer;

    public QuestionJpaEntity() {
    }

    public QuestionJpaEntity(String text, ConversationJpaEntity conversation, AnswerJpaEntity answer) {
        this.id = UUID.randomUUID();
        this.text = text;
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
