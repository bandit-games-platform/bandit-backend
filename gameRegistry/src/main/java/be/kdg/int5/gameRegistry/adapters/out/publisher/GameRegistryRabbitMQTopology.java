package be.kdg.int5.gameRegistry.adapters.out.publisher;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameRegistryRabbitMQTopology {
    public static final String GAME_REGISTRY_EVENTS_EXCHANGE = "game_registry_events";
    public static final String GAME_REGISTERED_QUEUE = "game_registered";
    public static final String GAME_REGISTERED_STOREFRONT_QUEUE = "game_registered_storefront";

    @Bean
    TopicExchange gameRegistryEventExchange() {
        return new TopicExchange(GAME_REGISTRY_EVENTS_EXCHANGE);
    }

    @Bean
    Queue gameRegisteredQueue() {
        return new Queue(GAME_REGISTERED_QUEUE);
    }

    @Bean
    Queue gameRegisteredStorefrontQueue() {
        return new Queue(GAME_REGISTERED_STOREFRONT_QUEUE);
    }

    @Bean
    Binding gameRegisteredBinding(TopicExchange gameRegistryEventExchange, Queue gameRegisteredQueue) {
        return BindingBuilder
                .bind(gameRegisteredQueue)
                .to(gameRegistryEventExchange)
                .with("game.*.registered");

    }

    @Bean
    Binding gameRegisteredStorefrontBinding(TopicExchange gameRegistryEventExchange, Queue gameRegisteredStorefrontQueue) {
        return BindingBuilder
                .bind(gameRegisteredStorefrontQueue)
                .to(gameRegistryEventExchange)
                .with("game.*.registered");

    }
}
