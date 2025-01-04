package be.kdg.int5.gameplay.port.out;

import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;

public interface LobbyLoadPort {
    Lobby load(LobbyId lobbyId);

    Lobby loadByOwnerIdAndGameId(PlayerId ownerId, GameId gameId);
}
