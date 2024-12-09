package be.kdg.int5.storefront.adapters.out.publisher;

import be.kdg.int5.common.events.GameRegisteredEvent;
import be.kdg.int5.common.events.OrderCompletedCommand;
import be.kdg.int5.storefront.domain.Order;
import be.kdg.int5.storefront.domain.OrderStatus;
import be.kdg.int5.storefront.port.out.OrderUpdatePort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PublishOrderCompletedAdapter implements OrderUpdatePort {
    private final RabbitTemplate rabbitTemplate;

    public PublishOrderCompletedAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void updateOrderStatus(Order order) {
        if (order.getOrderStatus() == OrderStatus.COMPLETED) {
            OrderCompletedCommand orderCompletedCommand = new OrderCompletedCommand(
                    order.getId().uuid(),
                    order.getCustomerId().uuid(),
                    order.getProductId().uuid()
            );

            String routing = "order.completed";

            rabbitTemplate.convertAndSend(
                    StorefrontRabbitMQTopology.STOREFRONT_DIRECT_EXCHANGE,
                    routing, orderCompletedCommand);
        }
    }
}
