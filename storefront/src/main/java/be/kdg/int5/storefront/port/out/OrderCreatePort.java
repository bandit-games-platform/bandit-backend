package be.kdg.int5.storefront.port.out;

import be.kdg.int5.storefront.domain.Order;

public interface OrderCreatePort {
    Order createNewOrder(Order order);
}
