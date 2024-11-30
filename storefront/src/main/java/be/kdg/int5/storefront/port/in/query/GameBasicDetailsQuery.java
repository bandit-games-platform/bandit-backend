package be.kdg.int5.storefront.port.in.query;

import java.util.Map;
import java.util.UUID;

public interface GameBasicDetailsQuery {
    Map<String, String> getBasicGameDetails(UUID gameId);
}
