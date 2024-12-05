package be.kdg.int5.player.adapters.out.db.playerLibrary;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class PlayerLibraryItemEmbeddable {
    private UUID gameId;
    private boolean favourite;
    private boolean hidden;

    public PlayerLibraryItemEmbeddable() {
    }

    public PlayerLibraryItemEmbeddable(UUID gameId, boolean favourite, boolean hidden) {
        this.gameId = gameId;
        this.favourite = favourite;
        this.hidden = hidden;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
