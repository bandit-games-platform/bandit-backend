package be.kdg.int5.player.adapters.out.db.playerLibrary;

import be.kdg.int5.player.domain.GameId;
import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.domain.PlayerLibrary;
import be.kdg.int5.player.domain.PlayerLibraryItem;
import be.kdg.int5.player.port.out.playerLibrary.PlayerLibraryCreatePort;
import be.kdg.int5.player.port.out.playerLibrary.PlayerLibraryLoadPort;
import be.kdg.int5.player.port.out.playerLibrary.PlayerLibraryUpdatePort;
import org.springframework.stereotype.Repository;
import java.util.HashSet;
import java.util.Set;

@Repository
public class PlayerLibraryJpaAdapter implements PlayerLibraryLoadPort, PlayerLibraryCreatePort, PlayerLibraryUpdatePort {
    private final PlayerLibraryJpaRepository playerLibraryJpaRepository;

    public PlayerLibraryJpaAdapter(PlayerLibraryJpaRepository playerLibraryJpaRepository) {
        this.playerLibraryJpaRepository = playerLibraryJpaRepository;
    }

    @Override
    public void createNewPlayerLibrary(PlayerLibrary playerLibrary) {
        playerLibraryJpaRepository.save(toJpa(playerLibrary));
    }

    @Override
    public PlayerLibrary loadLibraryForPlayer(PlayerId playerId) {
        PlayerLibraryJpaEntity playerLibraryJpaEntity = playerLibraryJpaRepository.findByPlayerIdWithLibraryItems(playerId.uuid());
        if (playerLibraryJpaEntity == null) return null;
        return toDomain(playerLibraryJpaEntity);
    }

    @Override
    public void updatePlayerLibrary(PlayerLibrary playerLibrary) {
        PlayerLibraryJpaEntity loadedLibrary = playerLibraryJpaRepository.findByPlayerIdWithLibraryItems(
                playerLibrary.getPlayerId().uuid()
        );
        if (loadedLibrary == null) {
            createNewPlayerLibrary(playerLibrary);
            return;
        }

        for (PlayerLibraryItem playerLibraryItem: playerLibrary.getPlayerLibraryItems()) {
            PlayerLibraryItemEmbeddable loadedLibraryItem = loadedLibrary.getPlayerLibraryItems().stream()
                    .filter(libraryItem -> libraryItem.getGameId().equals(playerLibraryItem.getGameId().uuid()))
                    .findFirst().orElse(null);
            if (loadedLibraryItem == null) {
                loadedLibrary.addNewLibraryItem(new PlayerLibraryItemEmbeddable(
                        playerLibraryItem.getGameId().uuid(),
                        playerLibraryItem.isFavourite(),
                        playerLibraryItem.isHidden()
                ));
            } else {
                loadedLibrary.removeLibraryItem(loadedLibraryItem);
                loadedLibraryItem.setHidden(playerLibraryItem.isHidden());
                loadedLibraryItem.setFavourite(playerLibraryItem.isFavourite());
                loadedLibrary.addNewLibraryItem(loadedLibraryItem);
            }
        }

        playerLibraryJpaRepository.save(loadedLibrary);
    }

    private PlayerLibrary toDomain(PlayerLibraryJpaEntity playerLibraryJpaEntity) {
        Set<PlayerLibraryItem> playerLibraryItems = new HashSet<>();
        for (PlayerLibraryItemEmbeddable playerLibraryItemEmbeddable: playerLibraryJpaEntity.getPlayerLibraryItems()) {
            playerLibraryItems.add(new PlayerLibraryItem(
                new GameId(playerLibraryItemEmbeddable.getGameId()),
                playerLibraryItemEmbeddable.isFavourite(),
                playerLibraryItemEmbeddable.isHidden()
            ));
        }

        return new PlayerLibrary(new PlayerId(playerLibraryJpaEntity.getPlayerId()), playerLibraryItems);
    }

    private PlayerLibraryJpaEntity toJpa(PlayerLibrary playerLibrary) {
        Set<PlayerLibraryItemEmbeddable> playerLibraryItemEmbeddableSet = new HashSet<>();
        for (PlayerLibraryItem playerLibraryItem : playerLibrary.getPlayerLibraryItems()) {
            playerLibraryItemEmbeddableSet.add(new PlayerLibraryItemEmbeddable(
                    playerLibraryItem.getGameId().uuid(),
                    playerLibraryItem.isFavourite(),
                    playerLibraryItem.isHidden()
            ));
        }

        return new PlayerLibraryJpaEntity(playerLibrary.getPlayerId().uuid(), playerLibraryItemEmbeddableSet);
    }
}
