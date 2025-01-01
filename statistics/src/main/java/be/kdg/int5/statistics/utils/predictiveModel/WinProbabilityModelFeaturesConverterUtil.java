package be.kdg.int5.statistics.utils.predictiveModel;

import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityFeaturesDto;
import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.GameEndState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Component
public class WinProbabilityModelFeaturesConverterUtil {
    private static final Logger logger = LoggerFactory.getLogger(WinProbabilityModelFeaturesConverterUtil.class);

    public WinProbabilityFeaturesDto convertToInputFeatures(
            UUID playerOne,
            List<CompletedSession> playerOneSessions,
            UUID playerTwo,
            List<CompletedSession> playerTwoSessions) {

        logger.info("Starting conversion of input features for players {} and {}", playerOne, playerTwo);

        float[] playerOneFeatures = calculateFeatures(playerOneSessions);
        float[] playerTwoFeatures = calculateFeatures(playerTwoSessions);

        float diffSessionCount = playerOneFeatures[0] - playerTwoFeatures[0];
        float diffWinRate = playerOneFeatures[1] - playerTwoFeatures[1];
        float diffAvgScore = playerOneFeatures[2] - playerTwoFeatures[2];
        float diffAvgTurnsTaken = playerOneFeatures[3] - playerTwoFeatures[3];
        float diffAvgSessionDuration = playerOneFeatures[4] - playerTwoFeatures[4];
        float diffAvgSecondsPerTurn = playerOneFeatures[5] - playerTwoFeatures[5];
        float diffAvgClicks = playerOneFeatures[6] - playerTwoFeatures[6];
        float diffAvgOpponentScore = playerOneFeatures[7] - playerTwoFeatures[7];
        float diffAvgStartingFirst = playerOneFeatures[8] - playerTwoFeatures[8];

        logger.info("Feature differences calculated for players {} and {}", playerOne, playerTwo);

        return new WinProbabilityFeaturesDto(
                playerOne,
                playerTwo,
                diffSessionCount,
                diffWinRate,
                diffAvgScore,
                diffAvgTurnsTaken,
                diffAvgSessionDuration,
                diffAvgSecondsPerTurn,
                diffAvgClicks,
                diffAvgOpponentScore,
                diffAvgStartingFirst
        );
    }

    private float[] calculateFeatures(List<CompletedSession> sessions) {
        logger.info("Starting calculation of features for {} sessions", sessions.size());

        int sessionsCount = sessions.size();
        float avgTurnsTaken = 0;
        float avgSessionDuration = 0;
        float avgScore = 0;
        float avgOpponentScore = 0;
        float winRate = 0;
        float avgStartingFirst = 0;
        float avgSecondsPerTurn = 0;
        float avgClicks = 0;

        if (!sessions.isEmpty()) {
            int totalTurnsTaken = sessions.stream()
                    .filter(session -> session.getTurnsTaken() != null)
                    .mapToInt(CompletedSession::getTurnsTaken)
                    .sum();

            avgTurnsTaken = (float) totalTurnsTaken / sessionsCount;

            float totalSessionDuration = (float) sessions.stream()
                    .mapToDouble(session -> Duration.between(session.getStartTime(), session.getEndTime()).toMinutes())
                    .sum();

            avgSessionDuration = totalSessionDuration / sessionsCount;

            int totalPlayerScore = sessions.stream()
                    .filter(session -> session.getPlayerScore() != null)
                    .mapToInt(CompletedSession::getPlayerScore)
                    .sum();

            avgScore = (float) totalPlayerScore / sessionsCount;

            int totalOpponentScore = sessions.stream()
                    .filter(session -> session.getOpponentScore() != null)
                    .mapToInt(CompletedSession::getOpponentScore)
                    .sum();

            avgOpponentScore = (float) totalOpponentScore / sessionsCount;

            long winCount = sessions.stream()
                    .filter(session -> session.getEndState() == GameEndState.WIN)
                    .count();

            winRate = (float) winCount / sessionsCount;

            long firstToGoCount = sessions.stream()
                    .filter(CompletedSession::getWasFirstToGo)
                    .count();

            avgStartingFirst = (float) firstToGoCount / sessionsCount;

            double totalSecondsForTurnAvg = sessions.stream()
                    .filter(session -> session.getAvgSecondsPerTurn() != null)
                    .mapToDouble(CompletedSession::getAvgSecondsPerTurn)
                    .sum();

            avgSecondsPerTurn = (float) totalSecondsForTurnAvg / sessionsCount;

            double totalClicks = sessions.stream()
                    .filter(session -> session.getClicks() != null)
                    .mapToDouble(CompletedSession::getClicks)
                    .sum();

            avgClicks = (float) totalClicks / sessionsCount;
        }

        logger.info(
                "Features calculated: " +
                        "Sessions count: {}, " +
                        "Avg turns: {}, " +
                        "Avg session duration: {}, " +
                        "Avg score: {}, " +
                        "Avg opponent score: {}, " +
                        "Win rate: {}, " +
                        "Avg first-to-go: {}, " +
                        "Avg seconds per turn: {}, " +
                        "Avg clicks: {}",
                sessionsCount,
                avgTurnsTaken,
                avgSessionDuration,
                avgScore,
                avgOpponentScore,
                winRate,
                avgStartingFirst,
                avgSecondsPerTurn,
                avgClicks
        );

        return new float[]{
                sessionsCount,
                winRate,
                avgScore,
                avgTurnsTaken,
                avgSessionDuration,
                avgSecondsPerTurn,
                avgClicks,
                avgOpponentScore,
                avgStartingFirst
        };
    }
}
