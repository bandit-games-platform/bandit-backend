package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;

public interface LobbyLoadPort {
    Lobby load(LobbyId lobbyId);
    Lobby loadWithoutInvites(LobbyId lobbyId);
}
