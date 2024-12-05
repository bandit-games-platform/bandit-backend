package be.kdg.int5.storefront.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrUpdateProductProjectionCommand(UUID productId, String title, BigDecimal price) {
}
