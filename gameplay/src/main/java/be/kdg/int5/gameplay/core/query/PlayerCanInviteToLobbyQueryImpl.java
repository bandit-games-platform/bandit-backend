package be.kdg.int5.gameplay.core.query;

import be.kdg.int5.gameplay.domain.Lobby;
import be.kdg.int5.gameplay.domain.LobbyId;
import be.kdg.int5.gameplay.domain.PlayerId;
import be.kdg.int5.gameplay.port.in.query.PlayerCanInviteToLobbyQuery;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import org.springframework.stereotype.Service;

@Service
public class PlayerCanInviteToLobbyQueryImpl implements PlayerCanInviteToLobbyQuery {
    private final LobbyLoadPort lobbyLoadPort;

    public PlayerCanInviteToLobbyQueryImpl(LobbyLoadPort lobbyLoadPort) {
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public boolean canPlayerCurrentlyInviteOthersToLobby(PlayerId requestingPlayer, LobbyId lobbyId) {
        Lobby lobby = lobbyLoadPort.load(lobbyId);

        if (lobby == null || !lobby.getOwnerId().uuid().equals(requestingPlayer.uuid())) return false;

        return !lobby.isClosed();
    }
}
