package be.kdg.int5.common.events;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record GameRegisteredEvent(
        UUID eventId,
        UUID gameId,
        UUID developerId,
        String title,
        String description,
        BigDecimal currentPrice,
        String icon_url,
        String background_url,
        String current_host,
        Set<Achievement> achievementList,
        Set<Rule> rules
) {
    public record Achievement(
            UUID achievementId,
            String title,
            String description,
            int counter_total
    ) {}

    public record Rule(
            int stepNumber,
            String rule
    ) {}
}
