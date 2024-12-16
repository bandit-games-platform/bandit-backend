package be.kdg.int5.gameplay.port.in.query;

import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;

public interface PlayerCanInviteToLobbyQuery {
    boolean canPlayerCurrentlyInviteOthersToLobby(PlayerId requestingPlayer, LobbyId lobbyId);
}
