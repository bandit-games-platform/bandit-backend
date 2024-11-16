package be.kdg.int5.chatbot.domain;

import java.time.LocalDateTime;
import java.util.List;

public class PlatformConversation extends Conversation {
    private static final String platformDescription = "Something or other";
    private String currentPage;

    public PlatformConversation(UserId userId, LocalDateTime lastMessageTime, LocalDateTime startTime, String currentPage) {
        this(userId, startTime, lastMessageTime, null, currentPage);
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
}
