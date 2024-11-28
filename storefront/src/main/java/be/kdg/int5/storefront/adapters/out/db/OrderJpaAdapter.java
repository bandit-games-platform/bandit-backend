package be.kdg.int5.storefront.adapters.out.db;

import be.kdg.int5.storefront.domain.*;
import be.kdg.int5.storefront.port.out.OrderCreatePort;
import be.kdg.int5.storefront.port.out.OrderLoadPort;
import be.kdg.int5.storefront.port.out.OrderUpdatePort;
import org.springframework.stereotype.Repository;

@Repository
public class OrderJpaAdapter implements OrderLoadPort, OrderUpdatePort, OrderCreatePort {
    private final OrderJpaRepository orderJpaRepository;

    public OrderJpaAdapter(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order createNewOrder(Order order) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.save(orderDomainToJpa(order));
        return orderJpaToDomain(orderJpaEntity);
    }

    @Override
    public Order loadOrderById(OrderId orderId) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findById(orderId.uuid()).orElse(null);
        if (orderJpaEntity == null) return null;
        return orderJpaToDomain(orderJpaEntity);
    }

    @Override
    public Order loadCompletedOrderByProductAndCustomer(ProductId productId, CustomerId customerId) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findCompletedByProductIdAndCustomerId(
                productId.uuid(),
                customerId.uuid()
        );
        if (orderJpaEntity == null) return null;
        return orderJpaToDomain(orderJpaEntity);
    }

    @Override
    public void updateOrderStatus(Order order) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findById(order.getId().uuid()).orElse(null);
        if (orderJpaEntity == null) {
            createNewOrder(order);
            return;
        }
        orderJpaEntity.setOrderStatus(order.getOrderStatus());
        if (order.getOrderStatus() == OrderStatus.COMPLETED) orderJpaEntity.setOrderStatus(order.getOrderStatus());
        orderJpaRepository.save(orderJpaEntity);
    }

    private Order orderJpaToDomain(OrderJpaEntity orderJpaEntity) {
        return new Order(
                new OrderId(orderJpaEntity.getId()),
                new ProductId(orderJpaEntity.getProductId()),
                new CustomerId(orderJpaEntity.getCustomerId()),
                orderJpaEntity.getOrderDate(),
                orderJpaEntity.getOrderStatus(),
                orderJpaEntity.getOrderCompletedAt()
        );
    }

    private OrderJpaEntity orderDomainToJpa(Order order) {
        return new OrderJpaEntity(
                order.getId().uuid(),
                order.getProductId().uuid(),
                order.getCustomerId().uuid(),
                order.getOrderDate(),
                order.getOrderCompletedAt(),
                order.getOrderStatus()
        );
    }
}
