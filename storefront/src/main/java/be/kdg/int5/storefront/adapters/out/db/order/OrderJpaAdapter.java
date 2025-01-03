package be.kdg.int5.storefront.adapters.out.db.order;

import be.kdg.int5.storefront.domain.*;
import be.kdg.int5.storefront.port.out.OrderCreatePort;
import be.kdg.int5.storefront.port.out.OrderLoadPort;
import be.kdg.int5.storefront.port.out.OrderUpdatePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
    public Order loadOrderByStripeIdAndProductAndCustomer(String sessionId, ProductId productId, CustomerId customerId) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findByProductIdAndCustomerIdAndStripeSessionId(
                sessionId,
                productId.uuid(),
                customerId.uuid()
        );
        if (orderJpaEntity == null) return null;
        return orderJpaToDomain(orderJpaEntity);
    }

    @Override
    public List<Order> loadPendingOrders() {
        List<OrderJpaEntity> orderJpaEntities = orderJpaRepository.findAllPendingOrders();
        if (orderJpaEntities == null) return null;
        return orderJpaEntities.stream().map(this::orderJpaToDomain).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(Order order) {
        OrderJpaEntity orderJpaEntity = orderJpaRepository.findById(order.getId().uuid()).orElse(null);
        if (orderJpaEntity == null) {
            createNewOrder(order);
            return;
        }
        orderJpaEntity.setOrderStatus(order.getOrderStatus());
        if (order.getOrderStatus() == OrderStatus.COMPLETED) orderJpaEntity.setOrderCompletedAt(order.getOrderCompletedAt());
        orderJpaRepository.save(orderJpaEntity);
    }

    @Override
    public List<Order> loadCompleteOrders() {
        List<OrderJpaEntity> orderJpaEntities = orderJpaRepository.findAllCompleteOrders();
        if (orderJpaEntities == null) return null;
        return orderJpaEntities.stream().map(this::orderJpaToDomain).collect(Collectors.toList());
    }

    @Override
    public List<Order> loadCompletedOrdersByCustomer(CustomerId customerId) {
        List<OrderJpaEntity> ordersJpa = orderJpaRepository.findAllCompletedOrdersByCustomerId(customerId.uuid());
        if (ordersJpa == null) return null;
        return ordersJpa.stream().map(this::orderJpaToDomain).toList();
    }

    private Order orderJpaToDomain(OrderJpaEntity orderJpaEntity) {
        return new Order(
                new OrderId(orderJpaEntity.getId()),
                orderJpaEntity.getStripeSessionId(),
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
                order.getStripeSessionId(),
                order.getProductId().uuid(),
                order.getCustomerId().uuid(),
                order.getOrderDate(),
                order.getOrderCompletedAt(),
                order.getOrderStatus()
        );
    }
}
