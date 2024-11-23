package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Conversation {
    private final UserId userId;
    private final LocalDateTime startTime;
    private LocalDateTime lastMessageTime;
    private List<Question> questions;

    public Conversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime) {
        this(userId, startTime, lastMessageTime, null);
    }

    public Conversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<Question> questions) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(startTime);

        this.userId = userId;
        this.startTime = startTime;
        this.lastMessageTime = Objects.requireNonNullElse(lastMessageTime, startTime);
        this.questions = Objects.requireNonNullElse(questions, new ArrayList<>());
    }

    public UserId getUserId() {
        return userId;
    }

    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

//    public abstract void addInitialQuestion();

    public void addQuestion(Question question, LocalDateTime questionSubmittedAt) {
        questions.add(question);
        this.setLastMessageTime(questionSubmittedAt);
    }
}
