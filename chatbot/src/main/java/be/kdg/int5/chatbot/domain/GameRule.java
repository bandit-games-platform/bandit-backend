package be.kdg.int5.chatbot.domain;

import java.util.Objects;

public record GameRule(int stepNumber, String rule) {
    public GameRule {
        if (stepNumber <= 0) {
            throw new IllegalArgumentException("stepNumber must be greater than 0.");
        }
        Objects.requireNonNull(rule);
    }
}
