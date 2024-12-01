package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.query.GameBasicDetailsQuery;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameBasicDetailsQueryImpl implements GameBasicDetailsQuery {
    private final ProductLoadPort productLoadPort;

    public GameBasicDetailsQueryImpl(ProductLoadPort productLoadPort) {
        this.productLoadPort = productLoadPort;
    }

    @Override
    public ProductProjection getBasicGameDetails(UUID gameId) {
        return productLoadPort.loadProductById(gameId);
    }
}
