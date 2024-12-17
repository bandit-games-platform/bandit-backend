package be.kdg.int5.gameplay.core.query;

import be.kdg.int5.gameplay.domain.*;
import be.kdg.int5.gameplay.port.in.query.VerifyDeveloperOwnsGameQuery;
import be.kdg.int5.gameplay.port.out.GameDeveloperProjectionLoadPort;
import be.kdg.int5.gameplay.port.out.LobbyLoadPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VerifyDeveloperOwnsGameQueryImpl implements VerifyDeveloperOwnsGameQuery {
    private final Logger logger = LoggerFactory.getLogger(VerifyDeveloperOwnsGameQueryImpl.class);

    private final GameDeveloperProjectionLoadPort gameDeveloperProjectionLoadPort;
    private final LobbyLoadPort lobbyLoadPort;

    public VerifyDeveloperOwnsGameQueryImpl(GameDeveloperProjectionLoadPort gameDeveloperProjectionLoadPort, LobbyLoadPort lobbyLoadPort) {
        this.gameDeveloperProjectionLoadPort = gameDeveloperProjectionLoadPort;
        this.lobbyLoadPort = lobbyLoadPort;
    }

    @Override
    public boolean verify(DeveloperId developerId, GameId gameId) {
        GameDeveloperProjection gdp = gameDeveloperProjectionLoadPort.loadByGameId(gameId);

        if (gdp == null) {
            logger.warn("gameplay: Could not find gameId '{}' in projection! Developer '{}' has been permitted. CHECK gameRegistry & rabbitMQ ASAP!",
                    gameId,
                    developerId
            );
            return true;
        }

        if (!gdp.developerId().equals(developerId)) {
            logger.warn("gameplay: Developer '{}' attempted to access game '{}' of developer '{}'",
                    developerId,
                    gameId,
                    gdp.developerId()
            );
            return false;
        }

        return true;
    }

    @Override
    public boolean verifyByLobbyId(DeveloperId developerId, LobbyId lobbyId) {
        Lobby lobby = lobbyLoadPort.load(lobbyId);

        if (lobby == null) return false;

        return verify(developerId, lobby.getGameId());
    }
}
