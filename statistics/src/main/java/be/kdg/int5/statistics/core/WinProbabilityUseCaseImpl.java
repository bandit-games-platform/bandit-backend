package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityFeaturesDto;
import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.port.in.WinProbabilityCommand;
import be.kdg.int5.statistics.port.in.WinProbabilityUseCase;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import be.kdg.int5.statistics.port.out.PredictWinProbabilityPort;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;
import be.kdg.int5.statistics.utils.predictiveModel.WinProbabilityModelFeaturesConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WinProbabilityUseCaseImpl implements WinProbabilityUseCase {
    private static final Logger logger = LoggerFactory.getLogger(WinProbabilityUseCaseImpl.class);

    private final CompletedSessionLoadPort completedSessionLoadPort;
    private final WinProbabilityModelFeaturesConverterUtil winProbabilityModelFeaturesConverterUtil;
    private final PredictWinProbabilityPort predictWinProbabilityPort;

    public WinProbabilityUseCaseImpl(CompletedSessionLoadPort completedSessionLoadPort, WinProbabilityModelFeaturesConverterUtil winProbabilityModelFeaturesConverterUtil, PredictWinProbabilityPort predictWinProbabilityPort) {
        this.completedSessionLoadPort = completedSessionLoadPort;
        this.winProbabilityModelFeaturesConverterUtil = winProbabilityModelFeaturesConverterUtil;
        this.predictWinProbabilityPort = predictWinProbabilityPort;
    }

    @Override
    public WinPrediction predictWinProbability(WinProbabilityCommand command) {
        List<CompletedSession> player1Sessions = completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                command.gameId(),
                command.player1()
        );
        List<CompletedSession> player2Sessions = completedSessionLoadPort.loadAllCompletedSessionsForGameAndPlayer(
                command.gameId(),
                command.player2()
        );

        validateSessions(player1Sessions, player2Sessions);

        logger.info("Predicting win probability for {} players: {} VS {}", command.gameId(), command.player1(), command.player2());

        WinProbabilityFeaturesDto featuresDto = winProbabilityModelFeaturesConverterUtil.convertToInputFeatures(
                command.player1().uuid(),
                player1Sessions,
                command.player2().uuid(),
                player2Sessions
        );

        return predictWinProbabilityPort.getPredictions(featuresDto);
    }

    private void validateSessions(List<CompletedSession> player1Sessions, List<CompletedSession> player2Sessions) {
        if (player1Sessions == null) {
            throw new IllegalStateException("Player 1 has no completed sessions for the selected game.");
        }
        if (player2Sessions == null) {
            throw new IllegalStateException("Player 2 has no completed sessions for the selected game.");
        }
    }
}
