package be.kdg.int5.statistics.utils.predictiveModel;

import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityModelFeaturesDto;
import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.GameEndState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.time.Duration;

@Component
public class WinProbabilityModelFeaturesConverterUtil {
    private static final Logger logger = LoggerFactory.getLogger(WinProbabilityModelFeaturesConverterUtil.class);

    public List<WinProbabilityModelFeaturesDto> convertToInputFeatures(
            UUID playerOne,
            List<CompletedSession> playerOneSessions,
            UUID playerTwo,
            List<CompletedSession> playerTwoSessions) {

        WinProbabilityModelFeaturesDto playerOneFeatures = calculateAggregatedFeatures(
                playerOne,
                playerOneSessions
        );

        WinProbabilityModelFeaturesDto playerTwoFeatures = calculateAggregatedFeatures(
                playerTwo,
                playerTwoSessions
        );

        return List.of(playerOneFeatures, playerTwoFeatures);
    }

    private WinProbabilityModelFeaturesDto calculateAggregatedFeatures(UUID playerId, List<CompletedSession> sessions) {
        logger.info("Win probability: Starting calculation of aggregated features for player: {}", playerId);

        float avgTurnsTaken = 0;
        float avgSessionDuration = 0;
        float avgScore = 0;
        float avgOpponentScore = 0;
        float winRate = 0;
        float avgStartingFirst = 0;
        float avgSecondsPerTurn = 0;
        float avgClicks = 0;

        int totalTurnsTaken = sessions.stream()
                .filter(session -> session.getTurnsTaken() != null)
                .mapToInt(CompletedSession::getTurnsTaken)
                .sum();

        avgTurnsTaken = sessions.isEmpty() ? 0 : (float) totalTurnsTaken / sessions.size();
        logger.info("Total turns taken: {}, Average turns per session: {}", totalTurnsTaken, avgTurnsTaken);

        float totalSessionDuration = (float) sessions.stream()
                .mapToDouble(session -> Duration.between(session.getStartTime(), session.getEndTime()).toMinutes())
                .sum();

        avgSessionDuration = sessions.isEmpty() ? 0 : totalSessionDuration / sessions.size();
        logger.info("Total session duration (in minutes): {}, Average session duration: {}", totalSessionDuration, avgSessionDuration);

        int totalPlayerScore = sessions.stream()
                .filter(session -> session.getPlayerScore() != null)
                .mapToInt(CompletedSession::getPlayerScore)
                .sum();

        avgScore = sessions.isEmpty() ? 0 : (float) totalPlayerScore / sessions.size();
        logger.info("Total player score: {}, Average player score: {}", totalPlayerScore, avgScore);

        int totalOpponentScore = sessions.stream()
                .filter(session -> session.getOpponentScore() != null)
                .mapToInt(CompletedSession::getOpponentScore)
                .sum();

        avgOpponentScore = sessions.isEmpty() ? 0 : (float) totalOpponentScore / sessions.size();
        logger.info("Total opponent score: {}, Average opponent score: {}", totalOpponentScore, avgOpponentScore);

        long winCount = sessions.stream()
                .filter(session -> session.getEndState() == GameEndState.WIN)
                .count();

        winRate = sessions.isEmpty() ? 0 : (float) winCount / sessions.size();
        logger.info("Total wins: {}, Win rate: {}", winCount, winRate);

        long firstToGoCount = sessions.stream()
                .filter(CompletedSession::getWasFirstToGo)
                .count();

        avgStartingFirst = sessions.isEmpty() ? 0 : (float) firstToGoCount / sessions.size();
        logger.info("Total first-to-go count: {}, Average starting first: {}", firstToGoCount, avgStartingFirst);

        double totalSecondsForTurnAvg = sessions.stream()
                .filter(session -> session.getAvgSecondsPerTurn() != null)
                .mapToDouble(CompletedSession::getAvgSecondsPerTurn)
                .sum();

        avgSecondsPerTurn = sessions.isEmpty() ? 0 : (float) totalSecondsForTurnAvg / sessions.size();
        logger.info("Total seconds per turn: {}, Average seconds per turn: {}", totalSessionDuration, avgSecondsPerTurn);

        double totalClicks = sessions.stream()
                .filter(session -> session.getClicks() != null)
                .mapToDouble(CompletedSession::getClicks)
                .sum();

        avgClicks = sessions.isEmpty() ? 0 : (float) totalClicks / sessions.size();
        logger.info("Total clicks: {}, Average clicks: {}", totalSessionDuration, avgClicks);

        WinProbabilityModelFeaturesDto result = new WinProbabilityModelFeaturesDto(
                playerId,
                avgTurnsTaken,
                avgSessionDuration,
                avgScore,
                avgOpponentScore,
                avgSecondsPerTurn,
                winRate,
                avgStartingFirst,
                avgClicks
        );

        logger.info("Completed calculation of aggregated features for player: {}", playerId);

        return result;
    }
}
