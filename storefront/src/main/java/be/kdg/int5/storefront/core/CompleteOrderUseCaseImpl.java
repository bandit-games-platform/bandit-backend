package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.CustomerId;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.OrderStatus;
import be.kdg.int5.storefront.domain.ProductId;
import be.kdg.int5.storefront.port.in.CompleteOrderCommand;
import be.kdg.int5.storefront.port.in.CompleteOrderUseCase;
import be.kdg.int5.storefront.port.out.OrderLoadPort;
import be.kdg.int5.storefront.port.out.OrderUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompleteOrderUseCaseImpl implements CompleteOrderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CompleteOrderUseCaseImpl.class);
    private final OrderLoadPort orderLoadPort;
    private final List<OrderUpdatePort> orderUpdatePort;

    public CompleteOrderUseCaseImpl(OrderLoadPort orderLoadPort, List<OrderUpdatePort> orderUpdatePort) {
        this.orderLoadPort = orderLoadPort;
        this.orderUpdatePort = orderUpdatePort;
    }

    @Override
    public boolean completeOrder(CompleteOrderCommand command) {
        ProductId productId = new ProductId(command.productId());
        CustomerId customerId = new CustomerId(command.customerId());
        Order order = orderLoadPort.loadOrderByProductAndCustomerAndStripeId(
                command.stripeSessionId(),
                productId,
                customerId
        );
        if (order == null) return false;
        if (order.getOrderStatus() == OrderStatus.COMPLETED) return true;
        order.completeOrder();

        logger.info("storefront: Order {} has now been {} at time {}", order.getId().uuid(), order.getOrderStatus(), order.getOrderCompletedAt());
        for (OrderUpdatePort updatePort : orderUpdatePort) {
            updatePort.updateOrderStatus(order);
        }
        return true;
    }
}
