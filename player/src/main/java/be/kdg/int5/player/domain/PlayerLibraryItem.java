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
}
