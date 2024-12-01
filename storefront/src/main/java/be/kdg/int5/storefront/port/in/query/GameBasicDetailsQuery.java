package be.kdg.int5.storefront.port.in.query;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.Map;
import java.util.UUID;

public interface GameBasicDetailsQuery {
    ProductProjection getBasicGameDetails(UUID gameId);
}
