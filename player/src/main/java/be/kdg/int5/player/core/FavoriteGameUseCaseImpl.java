package be.kdg.int5.player.core;

import be.kdg.int5.common.exceptions.GameNotFoundException;
import be.kdg.int5.player.domain.*;
import be.kdg.int5.player.port.in.FavoriteGameCommand;
import be.kdg.int5.player.port.in.FavoriteGameUseCase;
import be.kdg.int5.player.port.out.playerLibrary.PlayerLibraryLoadPort;
import be.kdg.int5.player.port.out.playerLibrary.PlayerLibraryUpdatePort;
import org.springframework.stereotype.Service;

@Service
public class FavoriteGameUseCaseImpl implements FavoriteGameUseCase {
    private final PlayerLibraryUpdatePort playerLibraryUpdatePort;
    private final PlayerLibraryLoadPort playerLibraryLoadPort;

    public FavoriteGameUseCaseImpl(
            final PlayerLibraryUpdatePort playerLibraryUpdatePort,
            final PlayerLibraryLoadPort playerLibraryLoadPort
    ) {
        this.playerLibraryUpdatePort = playerLibraryUpdatePort;
        this.playerLibraryLoadPort = playerLibraryLoadPort;
    }

    @Override
    public void changeGameFavoriteStatus(FavoriteGameCommand command) {
        PlayerId playerId = command.playerId();
        GameId gameId = command.gameId();
        boolean newStatus = command.newFavouriteStatus();
        PlayerLibrary playerLibrary = playerLibraryLoadPort.loadLibraryForPlayer(playerId);
        PlayerLibraryItem playerLibraryItem = playerLibrary.getPlayerLibraryItems()
                .stream()
                .filter(libraryItem -> libraryItem.getGameId().uuid().equals(gameId.uuid()))
                .findFirst()
                .orElseThrow(GameNotFoundException::new);
        playerLibraryItem.setFavourite(newStatus);
        playerLibraryUpdatePort.updatePlayerLibrary(playerLibrary);
        System.out.println("Game updated successfully");
    }
}