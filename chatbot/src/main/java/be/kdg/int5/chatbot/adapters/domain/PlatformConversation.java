package be.kdg.int5.chatbot.adapters.domain;

import java.time.LocalDateTime;
import java.util.List;

public class PlatformConversation extends Conversation {
    public static final String platformDescription = "Something or other";
    private String currentPage;

    public PlatformConversation(UserId userId, LocalDateTime lastMessageTime, LocalDateTime startTime, String currentPage) {
        super(userId, lastMessageTime, startTime);
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
}
