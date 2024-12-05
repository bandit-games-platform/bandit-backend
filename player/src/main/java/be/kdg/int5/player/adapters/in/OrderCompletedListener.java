package be.kdg.int5.player.adapters.in;

import be.kdg.int5.common.events.OrderCompletedCommand;
import be.kdg.int5.player.domain.GameId;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.AddGameToPlayerLibraryCommand;
import be.kdg.int5.player.port.in.AddGameToPlayerLibraryUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCompletedListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderCompletedListener.class);
    private final AddGameToPlayerLibraryUseCase addGameToPlayerLibraryUseCase;

    public OrderCompletedListener(AddGameToPlayerLibraryUseCase addGameToPlayerLibraryUseCase) {
        this.addGameToPlayerLibraryUseCase = addGameToPlayerLibraryUseCase;
    }

    @RabbitListener(queues = "order_completed", messageConverter = "#{jackson2JsonMessageConverter}")
    public void orderCompleted(OrderCompletedCommand orderCompletedCommand) {
        logger.info("player: New completed order {} for game {} and player {}",
                orderCompletedCommand.orderId(),
                orderCompletedCommand.productId(),
                orderCompletedCommand.customerId());

        addGameToPlayerLibraryUseCase.addGameToPlayerLibrary(new AddGameToPlayerLibraryCommand(
                new PlayerId(orderCompletedCommand.customerId()),
                new GameId(orderCompletedCommand.productId())
        ));
    }
}
