package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.RegisterPlayerCommand;
import be.kdg.int5.player.port.in.RegisterPlayerUseCase;
import be.kdg.int5.player.port.out.CreatePlayerPort;
import be.kdg.int5.player.port.out.LoadPlayerPort;
import be.kdg.int5.player.port.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterPlayerUseCaseImpl implements RegisterPlayerUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPlayerUseCaseImpl.class);
    private final CreatePlayerPort createPlayerPort;
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public RegisterPlayerUseCaseImpl(CreatePlayerPort createPlayerPort, LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.createPlayerPort = createPlayerPort;
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    public void registerPlayer(RegisterPlayerCommand command) {
        Player player = loadPlayerPort.loadPlayerById(command.id());

        if (player != null) {
            player.setDisplayName(command.username());
            updatePlayerPort.updatePlayerDisplayName(player);
            LOGGER.info("Player updated with display name {}", command.username());
            return;
        }

        createPlayerPort.createPlayer(new Player(new PlayerId(command.id()), command.username()));
        LOGGER.info("New player persisted to database with username: {} and id: {}", command.username(), command.id());
    }
}
