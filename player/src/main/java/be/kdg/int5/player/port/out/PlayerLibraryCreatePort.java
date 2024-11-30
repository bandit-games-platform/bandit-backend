package be.kdg.int5.player.port.out;

import be.kdg.int5.player.domain.PlayerLibrary;

public interface PlayerLibraryCreatePort {
    void saveNewPlayerLibrary(PlayerLibrary playerLibrary);
}
