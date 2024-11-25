package be.kdg.int5.gameRegistry.adapters.out.publisher;

import be.kdg.int5.common.events.GameRegisteredEvent;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.out.GamesCreatePort;
import be.kdg.int5.gameRegistry.port.out.GamesUpdatePort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PublishGameRegisteredEventAdapter implements GamesCreatePort, GamesUpdatePort {
    private final RabbitTemplate rabbitTemplate;

    public PublishGameRegisteredEventAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public boolean create(Game newGame) {
        GameRegisteredEvent gameRegisteredEvent = createEventFromGame(newGame);

        String routing = "game.%s.registered".formatted(newGame.getId().uuid());

        rabbitTemplate.convertAndSend(
                GameRegistryRabbitMQTopology.GAME_REGISTRY_EVENTS_EXCHANGE,
                routing, gameRegisteredEvent);

        return false;
    }

    @Override
    public boolean update(Game game) {
        GameRegisteredEvent gameRegisteredEvent = createEventFromGame(game);

        String routing = "game.%s.registered".formatted(game.getId().uuid());

        rabbitTemplate.convertAndSend(
                GameRegistryRabbitMQTopology.GAME_REGISTRY_EVENTS_EXCHANGE,
                routing, gameRegisteredEvent);

        return false;
    }

    private GameRegisteredEvent createEventFromGame(Game newGame) {
        return new GameRegisteredEvent(
                UUID.randomUUID(),
                newGame.getId().uuid(),
                newGame.getDeveloper().id().uuid(),
                newGame.getTitle(),
                newGame.getDescription(),
                newGame.getCurrentPrice(),
                newGame.getIcon().url().url(),
                newGame.getBackground().url().url(),
                newGame.getCurrentHost().url(),
                newGame.getAchievements().stream().map(
                        achievement -> new GameRegisteredEvent.Achievement(
                                achievement.getId().uuid(),
                                achievement.getTitle(),
                                achievement.getDescription(),
                                achievement.getCounterTotal()
                        )
                ).collect(Collectors.toSet()),
                newGame.getRules().stream().map(
                        rule -> new GameRegisteredEvent.Rule(
                                rule.stepNumber(),
                                rule.rule()
                        )
                ).collect(Collectors.toSet())
        );
    }
}
