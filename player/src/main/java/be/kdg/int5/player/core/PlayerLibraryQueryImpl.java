package be.kdg.int5.player.core;

import be.kdg.int5.player.domain.PlayerId;
import be.kdg.int5.player.domain.PlayerLibrary;
import be.kdg.int5.player.port.in.query.PlayerLibraryQuery;
import be.kdg.int5.player.port.out.PlayerLibraryLoadPort;
import org.springframework.stereotype.Service;

@Service
public class PlayerLibraryQueryImpl implements PlayerLibraryQuery {
    private final PlayerLibraryLoadPort playerLibraryLoadPort;

    public PlayerLibraryQueryImpl(PlayerLibraryLoadPort playerLibraryLoadPort) {
        this.playerLibraryLoadPort = playerLibraryLoadPort;
    }

    @Override
    public PlayerLibrary getPlayerLibrary(PlayerId playerId) {
        return playerLibraryLoadPort.loadLibraryForPlayer(playerId);
    }
}
