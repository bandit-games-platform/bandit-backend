package be.kdg.int5.storefront.port.out;

import java.util.Map;
import java.util.UUID;

public interface GameRegistryContextCallPort {
    Map<String, String> getGameTitleAndPriceFromId(UUID gameId);
}
