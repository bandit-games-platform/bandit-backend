package be.kdg.int5.chatbot.ports.in;

import be.kdg.int5.chatbot.domain.GameRule;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record CreateOrUpdateGameProjectionCommand(UUID gameId, String title, String description, Set<GameRule> rules) {
}
