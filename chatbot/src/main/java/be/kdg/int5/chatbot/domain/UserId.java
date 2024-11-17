package be.kdg.int5.chatbot.domain;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID uuid) {
    public UserId {
        Objects.requireNonNull(uuid);
    }
}
