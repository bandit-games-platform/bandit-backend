package be.kdg.int5.player.port.out.playerLibrary;

import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.domain.PlayerLibrary;

public interface PlayerLibraryLoadPort {
    PlayerLibrary loadLibraryForPlayer(PlayerId playerId);
}
