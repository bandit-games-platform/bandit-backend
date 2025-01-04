package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.LobbyId;

public interface LobbyDeletePort {
    void deleteById(LobbyId lobbyId);
}
