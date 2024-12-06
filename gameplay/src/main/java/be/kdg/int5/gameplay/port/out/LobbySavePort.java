package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.Lobby;

public interface LobbySavePort {
    void save(Lobby lobby);
}
