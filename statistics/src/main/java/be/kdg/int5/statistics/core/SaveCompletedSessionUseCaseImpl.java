package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.adapters.out.db.playerGameStats.PlayerGameStatsJpaAdapter;
import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.SessionId;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionCommand;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionUseCase;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsUpdatePort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SaveCompletedSessionUseCaseImpl implements SaveCompletedSessionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(SaveCompletedSessionUseCaseImpl.class);
    private final PlayerGameStatisticsUpdatePort playerGameStatisticsUpdatePort;
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;

    public SaveCompletedSessionUseCaseImpl(PlayerGameStatisticsUpdatePort playerGameStatisticsUpdatePort, PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort) {
        this.playerGameStatisticsUpdatePort = playerGameStatisticsUpdatePort;
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
    }

    @Override
    @Transactional
    public void saveCompletedGameSession(SaveCompletedSessionCommand command) {
        PlayerGameStats playerGameStats;

        CompletedSession completedSession = new CompletedSession(
                new SessionId(UUID.randomUUID()),
                command.startTime(),
                command.endTime(),
                command.endState(),
                command.turnsTaken(),
                command.avgSecondsPerTurn(),
                command.playerScore(),
                command.opponentScore(),
                command.clicks(),
                command.character(),
                command.wasFirstToGo()
        );

        try {
            playerGameStats = playerGameStatisticsLoadPort.loadPlayerGameStat(command.playerId(), command.gameId());
            logger.info("Existing player game statistics already found for game {} and player {}", command.gameId().uuid(), command.playerId().uuid());
            playerGameStats.addCompletedGameSession(completedSession);

        } catch (NoSuchElementException e) {
            playerGameStats = new PlayerGameStats(command.playerId(), command.gameId(), completedSession);
            logger.info("New player game statistics created for game {} and player {}", command.gameId().uuid(), command.playerId().uuid());
        }

        playerGameStatisticsUpdatePort.addNewCompletedSession(playerGameStats);
    }
}
