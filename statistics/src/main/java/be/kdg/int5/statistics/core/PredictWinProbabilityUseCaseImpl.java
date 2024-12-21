package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityCommand;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityUseCase;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityModelFeaturesDto;
import be.kdg.int5.statistics.port.out.PredictWinProbabilityPort;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;
import be.kdg.int5.statistics.utils.predictiveModel.WinProbabilityModelFeaturesConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictWinProbabilityUseCaseImpl implements PredictWinProbabilityUseCase {
    private static final Logger logger = LoggerFactory.getLogger(PredictWinProbabilityUseCaseImpl.class);

    private final CompletedSessionLoadPort completedSessionLoadPort;
    private final WinProbabilityModelFeaturesConverterUtil winProbabilityModelFeaturesConverterUtil;
    private final PredictWinProbabilityPort predictWinProbabilityPort;

    public PredictWinProbabilityUseCaseImpl(CompletedSessionLoadPort completedSessionLoadPort, WinProbabilityModelFeaturesConverterUtil winProbabilityModelFeaturesConverterUtil, PredictWinProbabilityPort predictWinProbabilityPort) {
        this.completedSessionLoadPort = completedSessionLoadPort;
        this.winProbabilityModelFeaturesConverterUtil = winProbabilityModelFeaturesConverterUtil;
        this.predictWinProbabilityPort = predictWinProbabilityPort;
    }

    @Override
    public WinPrediction predictWinProbability(PredictWinProbabilityCommand command) {
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
            logger.error("Error loading player game stats: {}", e.getMessage());
        }
        logger.info("Predicting win probability for {} players: {} VS {}", command.gameId(), command.player1(), command.player2());

        List<WinProbabilityModelFeaturesDto> featuresDto = winProbabilityModelFeaturesConverterUtil.convertToInputFeatures(
                command.player1().uuid(),
                player1Sessions,
                command.player2().uuid(),
                player2Sessions);

        return predictWinProbabilityPort.getPredictions(featuresDto);
    }
}
