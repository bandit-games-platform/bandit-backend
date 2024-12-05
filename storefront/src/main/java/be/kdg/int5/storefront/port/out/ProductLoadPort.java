package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.UUID;

public interface ProductLoadPort {
    ProductProjection loadProductById(UUID productId);
}
