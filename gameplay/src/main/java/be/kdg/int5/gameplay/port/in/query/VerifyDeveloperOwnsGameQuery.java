package be.kdg.int5.gameplay.port.in.query;

import be.kdg.int5.gameplay.domain.DeveloperId;
import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.LobbyId;

public interface VerifyDeveloperOwnsGameQuery {
    boolean verify(DeveloperId developerId, GameId gameId);

    boolean verifyByLobbyId(DeveloperId developerId, LobbyId lobbyId);
}
