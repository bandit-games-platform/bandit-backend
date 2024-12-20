package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityCommand;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityUseCase;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import be.kdg.int5.statistics.utils.predictiveModel.PlayerChurnInputFeaturesGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictWinProbabilityUseCaseImpl implements PredictWinProbabilityUseCase {
    private static final Logger logger = LoggerFactory.getLogger(PredictWinProbabilityUseCaseImpl.class);

    private final CompletedSessionLoadPort completedSessionLoadPort;
    private final PlayerChurnInputFeaturesGeneratorUtil playerChurnInputFeaturesGeneratorUtil;

    public PredictWinProbabilityUseCaseImpl(CompletedSessionLoadPort completedSessionLoadPort, PlayerChurnInputFeaturesGeneratorUtil playerChurnInputFeaturesGeneratorUtil) {
        this.completedSessionLoadPort = completedSessionLoadPort;
        this.playerChurnInputFeaturesGeneratorUtil = playerChurnInputFeaturesGeneratorUtil;
    }

    @Override
    public List<PlayerGameStats> predictWinProbability(PredictWinProbabilityCommand command) {

        List<CompletedSession> player1Sessions = null;
        List<CompletedSession> player2Sessions = null;

        try {
            player1Sessions = completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                    command.gameId(),
                    command.player1()
            );
            player2Sessions = completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                    command.gameId(),
                    command.player2()
            );
        } catch (Exception e) {

            // TODO call util package to transofrm and sen to pythong

            logger.error("Error loading player game stats: {}", e.getMessage());
            throw new RuntimeException("Both players must have participated in the game to predict win probability.");
        }

        return List.of();
    }
}
