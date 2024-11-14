package be.kdg.int5.player.domain;

public class PlayerLibraryItem {
    private final GameId gameId;
    private boolean favourite;
    private boolean hidden;

    public PlayerLibraryItem(GameId gameId) {
        this.gameId = gameId;
    }
}
