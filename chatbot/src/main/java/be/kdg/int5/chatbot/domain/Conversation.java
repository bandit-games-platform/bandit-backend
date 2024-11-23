package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class Conversation {
    private final UserId userId;
    private final LocalDateTime startTime;
    private LocalDateTime lastMessageTime;
    private List<Question> questions;

    public Conversation(UserId userId) {
        this(userId, LocalDateTime.now(), null, null);
    }

    public Conversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime) {
        this.userId = userId;
        this.startTime = startTime;
        this.lastMessageTime = lastMessageTime;
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

    public abstract Question start();

    public Question addFollowUpQuestion(String text) {
        Question question = new Question(text, LocalDateTime.now());
        update(question);
        return question;
    }

    public void update(Question question) {
        // if question was added but no answer for it yet
        Optional<Question> existingQuestion = questions.stream()
                .filter(q -> q.getSubmittedAt().isEqual(question.getSubmittedAt()))
                .findAny();

        if (existingQuestion.isPresent()) {
            existingQuestion.get().update(question.getAnswer());
        } else {
            questions.add(question);
        }

        this.setLastMessageTime(question.getSubmittedAt());
    }
}
