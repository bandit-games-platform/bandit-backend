package be.kdg.int5.storefront.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrUpdateProductProjectionCommand(UUID productId, String title, UUID developerId, String description, BigDecimal price, String backgroundUrl) {
}
