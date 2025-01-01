package be.kdg.int5.storefront.port.in;

import be.kdg.int5.storefront.domain.ProductProjection;

import java.util.List;

public interface RecommendationUseCase {
    List<ProductProjection> recommendProducts(RecommendationCommand command);
}
