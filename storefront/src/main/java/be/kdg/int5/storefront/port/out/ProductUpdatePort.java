package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.ProductProjection;

public interface ProductUpdatePort {
    void updatedProductProjection(ProductProjection productProjection);
}
