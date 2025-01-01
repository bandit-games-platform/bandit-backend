package be.kdg.int5.storefront.port.in.query;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.List;

public interface ProductListQuery {
    List<ProductProjection> retrieveAllProducts();
}
