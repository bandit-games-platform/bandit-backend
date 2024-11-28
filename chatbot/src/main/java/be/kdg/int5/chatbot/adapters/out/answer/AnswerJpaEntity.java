package be.kdg.int5.chatbot.adapters.out.answer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(schema = "chatbot", name = "answer")
public class AnswerJpaEntity {
    @Id
    private UUID id;
    @Column(length = 10000)
    private String text;

    public AnswerJpaEntity() {
        this.id = UUID.randomUUID();
    }

    public AnswerJpaEntity(String text) {
        this.id = UUID.randomUUID();
        this.text = text;
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
}
