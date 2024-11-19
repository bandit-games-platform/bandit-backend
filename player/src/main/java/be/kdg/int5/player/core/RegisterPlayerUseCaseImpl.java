package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.Player;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.port.in.RegisterPlayerCommand;
import be.kdg.int5.player.port.in.RegisterPlayerUseCase;
import be.kdg.int5.player.port.out.PlayerCreatePort;
import be.kdg.int5.player.port.out.PlayerLoadPort;
import be.kdg.int5.player.port.out.PlayerUpdatePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterPlayerUseCaseImpl implements RegisterPlayerUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterPlayerUseCaseImpl.class);
    private final PlayerCreatePort playerCreatePort;
    private final PlayerLoadPort playerLoadPort;
    private final PlayerUpdatePort playerUpdatePort;

    public RegisterPlayerUseCaseImpl(PlayerCreatePort playerCreatePort, PlayerLoadPort playerLoadPort, PlayerUpdatePort playerUpdatePort) {
        this.playerCreatePort = playerCreatePort;
        this.playerLoadPort = playerLoadPort;
        this.playerUpdatePort = playerUpdatePort;
    }

    @Override
    @Transactional
    public void registerOrUpdatePlayerAccount(RegisterPlayerCommand command) {
        Player player = playerLoadPort.loadPlayerById(command.id());

        if (player != null) {
            player.setDisplayName(command.username());
            playerUpdatePort.updatePlayerDisplayName(player);
            LOGGER.info("player: Player updated with display name {}", command.username());
            return;
        }

        playerCreatePort.createPlayer(new Player(new PlayerId(command.id()), command.username()));
        LOGGER.info("player: New player persisted to database with username: {} and id: {}", command.username(), command.id());
    }
}
