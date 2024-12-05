package be.kdg.int5.player.domain;

import java.util.HashSet;
import java.util.Set;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

public class PlayerLibrary {
    private final PlayerId playerId;
    private Set<PlayerLibraryItem> playerLibraryItems;

    public PlayerLibrary(final PlayerId playerId) {
        this(playerId, null);
    }

    public PlayerLibrary(PlayerId playerId, Set<PlayerLibraryItem> playerLibraryItems) {
        requireNonNull(playerId);

        this.playerId = playerId;
        this.playerLibraryItems = requireNonNullElse(playerLibraryItems, new HashSet<>());
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

    public void addNewLibraryItem(PlayerLibraryItem playerLibraryItem) {
        this.playerLibraryItems.add(playerLibraryItem);
    }
}
