package be.kdg.int5.storefront.port.in;

import be.kdg.int5.storefront.domain.Order;

public interface SaveNewOrderUseCase {
    Order saveNewOrder(SaveNewOrderCommand command);
}
