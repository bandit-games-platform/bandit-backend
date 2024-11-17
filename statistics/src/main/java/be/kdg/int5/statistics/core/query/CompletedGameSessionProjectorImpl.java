package be.kdg.int5.statistics.core.query;

import be.kdg.int5.statistics.port.in.CompletedGameSessionCommand;
import be.kdg.int5.statistics.port.in.CompletedGameSessionProjector;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsUpdatePort;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CompletedGameSessionProjectorImpl implements CompletedGameSessionProjector {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CompletedGameSessionProjectorImpl.class);

    private final PlayerGameStatisticsUpdatePort playerGameStatisticsUpdatePort;

    public CompletedGameSessionProjectorImpl(final PlayerGameStatisticsUpdatePort playerGameStatisticsUpdatePort) {
        this.playerGameStatisticsUpdatePort = playerGameStatisticsUpdatePort;
    }

    @Override
    public void completedGameSession(CompletedGameSessionCommand command) {

    }
}
