package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

public class PlatformConversation extends Conversation {
    private static final String PLATFORM_DESCRIPTION = "Something or other";
    private String currentPage;

    public PlatformConversation(UserId userId, String currentPage) {
        this(userId, LocalDateTime.now(), null, null, currentPage);
    }

    public PlatformConversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime, String currentPage) {
        super(userId, startTime, lastMessageTime);
        this.currentPage = currentPage;
    }

    public PlatformConversation(UserId userId, LocalDateTime startTime, LocalDateTime lastMessageTime, List<Question> questions, String currentPage) {
        super(userId, startTime, lastMessageTime, questions);
        this.currentPage = currentPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    @Override
    public Question start() {
        LocalDateTime submittedAt = LocalDateTime.now();
        this.setLastMessageTime(submittedAt);
        return new Question(PLATFORM_DESCRIPTION, submittedAt, true);
    }
}
