package be.kdg.int5.player.port.out.playerLibrary;

import be.kdg.int5.player.domain.GameId;
import be.kdg.int5.player.domain.PlayerLibrary;

public interface PlayerLibraryUpdatePort {
    void updatePlayerLibrary(PlayerLibrary playerLibrary);
}
