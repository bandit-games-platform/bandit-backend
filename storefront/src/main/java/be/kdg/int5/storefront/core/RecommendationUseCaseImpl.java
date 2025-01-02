package be.kdg.int5.storefront.core;

import be.kdg.int5.common.exceptions.PythonServiceException;
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

import java.util.*;
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
        final List<ProductProjection> allProducts = productLoadPort.loadAllProducts();
        final List<Order> completedOrders = orderLoadPort.loadCompletedOrdersByCustomer(command.customerId());

        if (completedOrders.isEmpty()) {
            logger.info("No completed orders for customer {} - returning all products.", command.customerId());
            return allProducts;
        }

        final Set<ProductId> ownedProductIds = completedOrders.stream()
                .map(Order::getProductId)
                .collect(Collectors.toSet());

        final List<ProductProjection> ownedProducts = allProducts.stream()
                .filter(p -> ownedProductIds.contains(p.getProductId()))
                .toList();

        List<ProductProjection> recommendations = allProducts;
        try {
             recommendations = productRecommendationPort.getRecommendationsForCustomer(allProducts, ownedProducts);
        } catch (PythonServiceException e) {
            logger.error("Failed to obtain recommendations from Python Service for CustomerId {}: {}", command.customerId(), e.getMessage());
        }

        return recommendations;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductProjection> getTrendingProducts() {
        // load all completed orders
        List<Order> allCompleteOrders = orderLoadPort.loadCompleteOrders();

        System.out.println("All orders:");
        allCompleteOrders.forEach(System.out::println);

        Map<ProductId, Double> productOrderCountMap = new HashMap<>();

        for (Order order : allCompleteOrders) {
            productOrderCountMap.merge(order.getProductId(), 1.0, Double::sum);
        }

        System.out.println("Map:");
        System.out.println(productOrderCountMap);

        List<ProductId> mostPopularProductsIds = new ArrayList<>();

        double totalCount = allCompleteOrders.size();
        double threshold = 0.5;
        double thresholdCount = totalCount * threshold;

        for (ProductId key : productOrderCountMap.keySet()) {
            if (productOrderCountMap.get(key) > thresholdCount) {
                mostPopularProductsIds.add(key);
            }
        }

        List<ProductProjection> mostPopularProducts = mostPopularProductsIds.stream()
                .map(p -> productLoadPort.loadProductById(p.uuid()))
                .toList();

        if (mostPopularProducts.isEmpty() || mostPopularProducts.size() < 4) {

        }

//        List<ProductProjection> allProducts = productLoadPort.loadProductById();
        return mostPopularProducts;
    }
}
