package be.kdg.int5.player.port.in.query;

import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.domain.PlayerLibrary;

public interface PlayerLibraryQuery {
    PlayerLibrary getPlayerLibrary(PlayerId playerId);
}
