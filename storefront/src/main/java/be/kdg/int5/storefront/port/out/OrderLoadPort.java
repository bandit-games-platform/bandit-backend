package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.CustomerId;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.OrderId;
import be.kdg.int5.storefront.domain.ProductId;

import java.util.List;

public interface OrderLoadPort {
    Order loadOrderById(OrderId orderId);
    Order loadCompletedOrderByProductAndCustomer(ProductId productId, CustomerId customerId);
    Order loadOrderByStripeIdAndProductAndCustomer(String sessionId, ProductId productId, CustomerId customerId);
    List<Order> loadPendingOrders();
    List<Order> loadCompleteOrders();
    List<Order> loadCompletedOrdersByCustomer(CustomerId customerId);
}
