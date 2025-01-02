package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.ProductId;
import be.kdg.int5.storefront.domain.ProductProjection;
import be.kdg.int5.storefront.port.in.RecommendationCommand;
import be.kdg.int5.storefront.port.in.RecommendationUseCase;
import be.kdg.int5.storefront.port.out.OrderLoadPort;
import be.kdg.int5.storefront.port.out.ProductLoadPort;
import be.kdg.int5.storefront.port.out.ProductRecommendationPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationUseCaseImpl implements RecommendationUseCase {
    private final ProductLoadPort productLoadPort;
    private final OrderLoadPort orderLoadPort;
    private final ProductRecommendationPort productRecommendationPort;

    private final static Logger logger = LoggerFactory.getLogger(RecommendationUseCaseImpl.class);

    public RecommendationUseCaseImpl(
            ProductLoadPort productLoadPort,
            OrderLoadPort orderLoadPort,
            ProductRecommendationPort productRecommendationPort) {
        this.productLoadPort = productLoadPort;
        this.orderLoadPort = orderLoadPort;
        this.productRecommendationPort = productRecommendationPort;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductProjection> recommendProducts(RecommendationCommand command) {
        final List<ProductProjection> allProductsList = productLoadPort.loadAllProducts();
        final List<Order> completedOrders = orderLoadPort.loadCompletedOrdersByCustomer(command.customerId());

        if (completedOrders.isEmpty()) {
            return allProductsList;
        }

        final Set<ProductId> ownedProductIds = completedOrders.stream()
                .map(Order::getProductId)
                .collect(Collectors.toSet());

        logger.info(ownedProductIds.toString());

        final List<ProductProjection> ownedProducts = allProductsList.stream()
                .filter(p -> ownedProductIds.contains(p.getProductId()))
                .toList();

        return productRecommendationPort.getRecommendationsForCustomer(allProductsList, ownedProducts);
    }
}
