package be.kdg.int5.player.domain;
import static java.util.Objects.requireNonNull;

public class PlayerLibraryItem {
    private final GameId gameId;
    private boolean favourite;
    private boolean hidden;

    public PlayerLibraryItem(final GameId gameId) {
        requireNonNull(gameId);

        this.gameId = gameId;
    }

    public GameId getGameId() {
        return gameId;
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
