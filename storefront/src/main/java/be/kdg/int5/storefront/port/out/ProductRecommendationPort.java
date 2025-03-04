package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.List;

public interface ProductRecommendationPort {
    List<ProductProjection> getRecommendationsForCustomer(List<ProductProjection> allProductsList, List<ProductProjection> ownedProductsList);
}
