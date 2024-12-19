package be.kdg.int5.gameplay.port.in.query;

import be.kdg.int5.gameplay.domain.GameId;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;

public interface PlayerCanInviteToLobbyQuery {
    LobbyId canPlayerCurrentlyInviteOthersToLobby(PlayerId requestingOwner, GameId gameId);
}
