package be.kdg.int5.chatbot.adapters.in;

import be.kdg.int5.chatbot.domain.GameRule;
import be.kdg.int5.chatbot.ports.in.CreateOrUpdateGameProjectionCommand;
import be.kdg.int5.chatbot.ports.in.CreateOrUpdateGameProjectionUseCase;
import be.kdg.int5.common.events.GameRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameRegistrationListener {
    private static final Logger logger = LoggerFactory.getLogger(GameRegistrationListener.class);
    private final CreateOrUpdateGameProjectionUseCase createOrUpdateGameProjectionUseCase;

    public GameRegistrationListener(CreateOrUpdateGameProjectionUseCase createOrUpdateGameProjectionUseCase) {
        this.createOrUpdateGameProjectionUseCase = createOrUpdateGameProjectionUseCase;
    }

    @RabbitListener(queues = "game_registered", messageConverter = "#{jackson2JsonMessageConverter}")
    public void gameRegistered(GameRegisteredEvent gameRegisteredEvent) {
        logger.info("chatbot: New game update for game {} with description {} and title {}",
                gameRegisteredEvent.gameId(),
                gameRegisteredEvent.description(),
                gameRegisteredEvent.title());

        createOrUpdateGameProjectionUseCase.createOrUpdateGameProjection(
                new CreateOrUpdateGameProjectionCommand(
                        gameRegisteredEvent.gameId(),
                        gameRegisteredEvent.title(),
                        gameRegisteredEvent.description(),
                        gameRegisteredEvent.rules().stream().map(
                                rule -> new GameRule(rule.stepNumber(), rule.rule())
                        ).collect(Collectors.toSet())
                )
        );
    }
}
