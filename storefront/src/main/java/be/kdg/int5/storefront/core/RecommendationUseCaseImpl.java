package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.RecommendationCommand;
import be.kdg.int5.storefront.port.in.RecommendationUseCase;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import be.kdg.int5.storefront.port.out.ProductRecommendationPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecommendationUseCaseImpl implements RecommendationUseCase {
    private final ProductLoadPort productLoadPort;
    private final ProductRecommendationPort productRecommendationPort;

    public RecommendationUseCaseImpl(ProductLoadPort productLoadPort, ProductRecommendationPort productRecommendationPort) {
        this.productLoadPort = productLoadPort;
        this.productRecommendationPort = productRecommendationPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductProjection> recommendProducts(RecommendationCommand command) {
        final List<ProductProjection> productProjectionList = productLoadPort.loadAllProducts();

        productRecommendationPort.getRecommendationsForCustomer(productProjectionList);

        return productProjectionList;
    }
}
