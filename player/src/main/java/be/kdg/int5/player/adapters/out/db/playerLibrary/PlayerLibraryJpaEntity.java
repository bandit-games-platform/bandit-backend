package be.kdg.int5.player.adapters.out.db.playerLibrary;

import jakarta.persistence.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(schema = "player", name = "player_library")
public class PlayerLibraryJpaEntity {
    @Id
    private UUID playerId;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            schema = "player", name = "library_items",
            joinColumns = @JoinColumn(name = "player_id")
    )
    private Set<PlayerLibraryItemEmbeddable> playerLibraryItems;


    public PlayerLibraryJpaEntity() {
    }

    public PlayerLibraryJpaEntity(UUID playerId, Set<PlayerLibraryItemEmbeddable> playerLibraryItems) {
        this.playerId = playerId;
        this.playerLibraryItems = playerLibraryItems;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public Set<PlayerLibraryItemEmbeddable> getPlayerLibraryItems() {
        return playerLibraryItems;
    }

    public void setPlayerLibraryItems(Set<PlayerLibraryItemEmbeddable> playerLibraryItems) {
        this.playerLibraryItems = playerLibraryItems;
    }

    public void addNewLibraryItem(PlayerLibraryItemEmbeddable playerLibraryItemEmbeddable) {
        this.playerLibraryItems.add(playerLibraryItemEmbeddable);
    }

    public void removeLibraryItem(PlayerLibraryItemEmbeddable playerLibraryItemEmbeddable) {
        this.playerLibraryItems.remove(playerLibraryItemEmbeddable);
    }
}
