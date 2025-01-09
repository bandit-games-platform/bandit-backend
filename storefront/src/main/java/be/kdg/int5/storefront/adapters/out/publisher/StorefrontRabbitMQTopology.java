package be.kdg.int5.storefront.adapters.out.publisher;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorefrontRabbitMQTopology {
    public static final String STOREFRONT_DIRECT_EXCHANGE = "storefront_direct";
    public static final String ORDER_COMPLETED_QUEUE = "order_completed";

    @Bean
    DirectExchange storefrontDirectExchange() {
        return new DirectExchange(STOREFRONT_DIRECT_EXCHANGE);
    }

    @Bean
    Queue orderCompletedQueue() {
        return new Queue(ORDER_COMPLETED_QUEUE);
    }

    @Bean
    Binding orderCompletedBinding(DirectExchange storefrontDirectExchange, Queue orderCompletedQueue) {
        return BindingBuilder
                .bind(orderCompletedQueue)
                .to(storefrontDirectExchange)
                .with("order.completed");
    }
}
