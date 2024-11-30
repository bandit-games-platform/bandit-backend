package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.PlayerLibrary;
import be.kdg.int5.player.domain.PlayerLibraryItem;
import be.kdg.int5.player.port.in.AddGameToPlayerLibraryCommand;
import be.kdg.int5.player.port.in.AddGameToPlayerLibraryUseCase;
import be.kdg.int5.player.port.out.PlayerLibraryCreatePort;
import be.kdg.int5.player.port.out.PlayerLibraryLoadPort;
import be.kdg.int5.player.port.out.PlayerLibraryUpdatePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddGameToPlayerLibraryUseCaseImpl implements AddGameToPlayerLibraryUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AddGameToPlayerLibraryUseCaseImpl.class);
    private final PlayerLibraryLoadPort playerLibraryLoadPort;
    private final PlayerLibraryCreatePort playerLibraryCreatePort;
    private final PlayerLibraryUpdatePort playerLibraryUpdatePort;

    public AddGameToPlayerLibraryUseCaseImpl(PlayerLibraryLoadPort playerLibraryLoadPort, PlayerLibraryCreatePort playerLibraryCreatePort, PlayerLibraryUpdatePort playerLibraryUpdatePort) {
        this.playerLibraryLoadPort = playerLibraryLoadPort;
        this.playerLibraryCreatePort = playerLibraryCreatePort;
        this.playerLibraryUpdatePort = playerLibraryUpdatePort;
    }

    @Override
    @Transactional
    public void addGameToPlayerLibrary(AddGameToPlayerLibraryCommand command) {
        PlayerLibrary playerLibrary = playerLibraryLoadPort.loadLibraryForPlayer(command.playerId());
        if (playerLibrary != null) {
            if (playerLibrary.getPlayerLibraryItems().stream()
                    .anyMatch(libraryItem -> libraryItem.getGameId().uuid().equals(command.gameId().uuid()))
            ) {
                logger.warn("player: Game {} already exists in the players library!", command.gameId().uuid());
                return;
            }

            playerLibrary.addNewLibraryItem(new PlayerLibraryItem(command.gameId()));
            logger.info("player: New game {}, added to existing player library for player {}",
                    command.gameId().uuid(), command.playerId().uuid());
            playerLibraryUpdatePort.updatePlayerLibrary(playerLibrary);
        } else {
            playerLibrary = new PlayerLibrary(command.playerId());
            playerLibrary.addNewLibraryItem(new PlayerLibraryItem(command.gameId()));
            logger.info("player: New game {}, added to new player library for player {}",
                    command.gameId().uuid(), command.playerId().uuid());
            playerLibraryCreatePort.saveNewPlayerLibrary(playerLibrary);
        }
    }
}
