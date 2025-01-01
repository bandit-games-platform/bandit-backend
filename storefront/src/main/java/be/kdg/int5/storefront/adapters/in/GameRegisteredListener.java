package be.kdg.int5.storefront.adapters.in;

import be.kdg.int5.common.events.GameRegisteredEvent;
import be.kdg.int5.common.events.OrderCompletedCommand;
import be.kdg.int5.storefront.port.in.CreateOrUpdateProductProjectionCommand;
import be.kdg.int5.storefront.port.in.CreateOrUpdateProductProjectionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GameRegisteredListener {
    private static final Logger logger = LoggerFactory.getLogger(GameRegisteredListener.class);
    private final CreateOrUpdateProductProjectionUseCase createOrUpdateProductProjectionUseCase;

    public GameRegisteredListener(CreateOrUpdateProductProjectionUseCase createOrUpdateProductProjectionUseCase) {
        this.createOrUpdateProductProjectionUseCase = createOrUpdateProductProjectionUseCase;
    }

    @RabbitListener(queues = "game_registered_storefront", messageConverter = "#{jackson2JsonMessageConverter}")
    public void gameRegistered(GameRegisteredEvent gameRegisteredEvent) {
        logger.info("storefront: New game update for game {} with price {} and title {}",
                gameRegisteredEvent.gameId(),
                gameRegisteredEvent.currentPrice(),
                gameRegisteredEvent.title());

        createOrUpdateProductProjectionUseCase.createOrUpdateProductProjection(
                new CreateOrUpdateProductProjectionCommand(
                        gameRegisteredEvent.gameId(),
                        gameRegisteredEvent.title(),
                        gameRegisteredEvent.developerId(),
                        gameRegisteredEvent.description(),
                        gameRegisteredEvent.currentPrice(),
                        gameRegisteredEvent.background_url()
                )
        );
    }
}
