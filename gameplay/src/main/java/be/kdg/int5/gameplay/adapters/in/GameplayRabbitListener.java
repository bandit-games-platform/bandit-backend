package be.kdg.int5.gameplay.adapters.in;

import be.kdg.int5.common.events.GameRegisteredEvent;
import be.kdg.int5.gameplay.domain.DeveloperId;
import be.kdg.int5.gameplay.domain.GameDeveloperProjection;
import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.port.in.GameDeveloperProjectionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GameplayRabbitListener {
    private final Logger logger = LoggerFactory.getLogger(GameplayRabbitListener.class);

    private final GameDeveloperProjectionUseCase gameDeveloperProjectionUseCase;

    public GameplayRabbitListener(GameDeveloperProjectionUseCase gameDeveloperProjectionUseCase) {
        this.gameDeveloperProjectionUseCase = gameDeveloperProjectionUseCase;
    }

    @RabbitListener(queues = "game_registered_gameplay", messageConverter = "#{gameplayJackson2JsonMessageConverter}")
    public void gameRegistered(GameRegisteredEvent event) {
        logger.info("gameplay: updating game-developer projection with game '{}' and developer '{}'", event.gameId(), event.developerId());

        gameDeveloperProjectionUseCase.project(new GameDeveloperProjection(
                new GameId(event.gameId()),
                new DeveloperId(event.developerId())
        ));
    }
}
