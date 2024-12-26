package be.kdg.int5.chatbot.ports.in;

import java.util.UUID;

public record PlatformConversationCommand(
    String page,
    UUID userId
) {
}
