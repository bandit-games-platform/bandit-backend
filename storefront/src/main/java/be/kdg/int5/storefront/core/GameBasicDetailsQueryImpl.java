package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.port.in.query.GameBasicDetailsQuery;
import be.kdg.int5.storefront.port.out.GameRegistryContextCallPort;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class GameBasicDetailsQueryImpl implements GameBasicDetailsQuery {
    private final GameRegistryContextCallPort gameRegistryContextCallPort;

    public GameBasicDetailsQueryImpl(GameRegistryContextCallPort gameRegistryContextCallPort) {
        this.gameRegistryContextCallPort = gameRegistryContextCallPort;
    }

    @Override
    public Map<String, String> getBasicGameDetails(UUID gameId) {
        return gameRegistryContextCallPort.getGameTitleAndPriceFromId(gameId);
    }
}
