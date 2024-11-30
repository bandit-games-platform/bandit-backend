package be.kdg.int5.storefront.port.in;

import be.kdg.int5.storefront.domain.Order;

public interface CompleteOrderUseCase {
    boolean completeOrder(CompleteOrderCommand command);
}
