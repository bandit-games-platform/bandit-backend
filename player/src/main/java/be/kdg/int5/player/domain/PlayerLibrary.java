package be.kdg.int5.player.domain;

import java.util.HashSet;
import java.util.Set;

public class PlayerLibrary {
    private final PlayerId playerId;
    private Set<PlayerLibraryItem> playerLibraryItems;

    public PlayerLibrary(final PlayerId playerId) {
        this.playerId = playerId;
        this.playerLibraryItems = new HashSet<>();
    }
}
