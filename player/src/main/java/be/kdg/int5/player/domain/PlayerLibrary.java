package be.kdg.int5.player.domain;

import java.util.HashSet;
import java.util.Set;
import static java.util.Objects.requireNonNull;

public class PlayerLibrary {
    private final PlayerId playerId;
    private Set<PlayerLibraryItem> playerLibraryItems;

    public PlayerLibrary(final PlayerId playerId) {
        requireNonNull(playerId);

        this.playerId = playerId;
        this.playerLibraryItems = new HashSet<>();
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public Set<PlayerLibraryItem> getPlayerLibraryItems() {
        return playerLibraryItems;
    }

    public void setPlayerLibraryItems(Set<PlayerLibraryItem> playerLibraryItems) {
        this.playerLibraryItems = playerLibraryItems;
    }
}
