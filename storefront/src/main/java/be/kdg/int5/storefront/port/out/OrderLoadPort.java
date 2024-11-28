package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.CustomerId;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.OrderId;
import be.kdg.int5.storefront.domain.ProductId;

public interface OrderLoadPort {
    Order loadOrderById(OrderId orderId);
    Order loadCompletedOrderByProductAndCustomer(ProductId productId, CustomerId customerId);
}
