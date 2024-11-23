package be.kdg.int5.gameRegistry.port.in;

import be.kdg.int5.common.domain.ImageResource;
import be.kdg.int5.common.domain.ResourceURL;
import be.kdg.int5.gameRegistry.domain.DeveloperId;
import be.kdg.int5.gameRegistry.domain.Rule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public record RegisterGameCommand(
        DeveloperId developerId,
        String title,
        ResourceURL currentHost,
        String description,
        BigDecimal currentPrice,
        String icon,
        String background,
        Set<Rule> rules,
        List<AchievementRecord> achievements,
        List<ImageResource> screenshots
) {
    public RegisterGameCommand {
        Objects.requireNonNull(developerId);
        Objects.requireNonNull(title);
        Objects.requireNonNull(currentHost);
    }

    public record AchievementRecord(int uniqueNumber, String title, int counterTotal, String description) {
    }
}
