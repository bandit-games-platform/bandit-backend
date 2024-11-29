package be.kdg.int5.storefront.core;

import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.port.in.CancelAllOldOrdersUseCase;
import be.kdg.int5.storefront.port.out.OrderLoadPort;
import be.kdg.int5.storefront.port.out.OrderUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CancelAllOldOrdersUseCaseImpl implements CancelAllOldOrdersUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CancelAllOldOrdersUseCaseImpl.class);
    private final OrderLoadPort orderLoadPort;
    private final OrderUpdatePort orderUpdatePort;

    public CancelAllOldOrdersUseCaseImpl(OrderLoadPort orderLoadPort, OrderUpdatePort orderUpdatePort) {
        this.orderLoadPort = orderLoadPort;
        this.orderUpdatePort = orderUpdatePort;
    }

    @Override
    public void cancelAllOldPendingOrders() {
        List<Order> pendingOrders = orderLoadPort.loadPendingOrders();
        LocalDateTime currentTime = LocalDateTime.now();

        for (Order order : pendingOrders) {
            Duration duration = Duration.between(order.getOrderDate(), currentTime);
            if (duration.toHours() >= 12) {
                order.cancelOrder();
                logger.info("Order {} has now been {}, it was {} hours old", order.getId().uuid(), order.getOrderStatus(), duration.toHours());
                orderUpdatePort.updateOrderStatus(order);
            }
        }
    }
}
