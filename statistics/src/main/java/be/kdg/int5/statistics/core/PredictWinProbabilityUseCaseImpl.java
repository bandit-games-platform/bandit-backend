package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityCommand;
import be.kdg.int5.statistics.port.in.PredictWinProbabilityUseCase;
import be.kdg.int5.statistics.port.out.CompletedSessionLoadPort;
import be.kdg.int5.statistics.utils.predictiveModel.PredictWinProbabilityDto;
import be.kdg.int5.statistics.utils.predictiveModel.WinProbabilityInputFeaturesConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictWinProbabilityUseCaseImpl implements PredictWinProbabilityUseCase {
    private static final Logger logger = LoggerFactory.getLogger(PredictWinProbabilityUseCaseImpl.class);

    private final CompletedSessionLoadPort completedSessionLoadPort;
    private final WinProbabilityInputFeaturesConverterUtil winProbabilityInputFeaturesConverterUtil;

    public PredictWinProbabilityUseCaseImpl(CompletedSessionLoadPort completedSessionLoadPort, WinProbabilityInputFeaturesConverterUtil winProbabilityInputFeaturesConverterUtil) {
        this.completedSessionLoadPort = completedSessionLoadPort;
        this.winProbabilityInputFeaturesConverterUtil = winProbabilityInputFeaturesConverterUtil;
    }

    @Override
    public List<PredictWinProbabilityDto> predictWinProbability(PredictWinProbabilityCommand command) {
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

        return winProbabilityInputFeaturesConverterUtil.convertToInputFeatures(
                command.player1().uuid(),
                player1Sessions,
                command.player2().uuid(),
                player2Sessions);
    }
}
