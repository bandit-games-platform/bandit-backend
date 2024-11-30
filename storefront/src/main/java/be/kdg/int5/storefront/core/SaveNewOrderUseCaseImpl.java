package be.kdg.int5.storefront.core;

import be.kdg.int5.common.exceptions.OrderAlreadyExistsException;
import be.kdg.int5.storefront.domain.CustomerId;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.ProductId;
import be.kdg.int5.storefront.port.in.SaveNewOrderCommand;
import be.kdg.int5.storefront.port.in.SaveNewOrderUseCase;
import be.kdg.int5.storefront.port.out.OrderCreatePort;
import be.kdg.int5.storefront.port.out.OrderLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SaveNewOrderUseCaseImpl implements SaveNewOrderUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SaveNewOrderUseCaseImpl.class);
    private final OrderLoadPort orderLoadPort;
    private final OrderCreatePort orderCreatePort;

    public SaveNewOrderUseCaseImpl(OrderLoadPort orderLoadPort, OrderCreatePort orderCreatePort) {
        this.orderLoadPort = orderLoadPort;
        this.orderCreatePort = orderCreatePort;
    }

    @Override
    @Transactional
    public Order saveNewOrder(SaveNewOrderCommand command) {
        ProductId productId = new ProductId(command.productId());
        CustomerId customerId = new CustomerId(command.customerId());
        Order order = orderLoadPort.loadCompletedOrderByProductAndCustomer(productId, customerId);
        if (order != null) {
            logger.error("A completed order with this product and customer already exists");
            throw new OrderAlreadyExistsException("An order with this product and customer already exists");
        }

        order = new Order(new ProductId(
                command.productId()),
                new CustomerId(command.customerId()),
                LocalDateTime.now(),
                command.stripeSessionId()
        );
        orderCreatePort.createNewOrder(order);
        logger.info("New order created with id {} for customer {} and product {}",
                order.getId().uuid(), order.getCustomerId().uuid(), order.getProductId().uuid());
        return order;
    }
}
