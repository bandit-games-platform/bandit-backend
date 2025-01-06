package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.List;
import java.util.UUID;

public interface ProductLoadPort {
    ProductProjection loadProductById(UUID productId);
    ProductProjection loadProductByIdAllFields(UUID productId);
    List<ProductProjection> loadAllProducts();
}
