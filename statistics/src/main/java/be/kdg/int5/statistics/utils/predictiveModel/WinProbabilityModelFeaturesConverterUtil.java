package be.kdg.int5.statistics.utils.predictiveModel;

import be.kdg.int5.statistics.adapters.out.python.dto.WinPredictionModeInputFeaturesDto;
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

    public WinPredictionModeInputFeaturesDto convertToInputFeatures(
            UUID playerOne,
            List<CompletedSession> playerOneSessions,
            UUID playerTwo,
            List<CompletedSession> playerTwoSessions) {

        AggregatedPlayerSessionsData playerOneAgg = calculateAggregatedFeatures(
                playerOne,
                playerOneSessions
        );

        AggregatedPlayerSessionsData playerTwoAgg = calculateAggregatedFeatures(
                playerTwo,
                playerTwoSessions
        );

        return calculateModelInputFeaturesDto( playerOne, playerOneAgg, playerTwo,playerTwoAgg);
    }

    private AggregatedPlayerSessionsData calculateAggregatedFeatures(UUID playerId, List<CompletedSession> sessions) {
        logger.info("Win probability: Starting calculation of aggregated features for player: {}", playerId);

        int sessionsCount = sessions.size();
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

        avgTurnsTaken = sessions.isEmpty() ? 0 : (float) totalTurnsTaken / sessionsCount;
        logger.info("Total turns taken: {}, Average turns per session: {}", totalTurnsTaken, avgTurnsTaken);

        float totalSessionDuration = (float) sessions.stream()
                .mapToDouble(session -> Duration.between(session.getStartTime(), session.getEndTime()).toMinutes())
                .sum();

        avgSessionDuration = sessions.isEmpty() ? 0 : totalSessionDuration / sessionsCount;
        logger.info("Total session duration (in minutes): {}, Average session duration: {}", totalSessionDuration, avgSessionDuration);

        int totalPlayerScore = sessions.stream()
                .filter(session -> session.getPlayerScore() != null)
                .mapToInt(CompletedSession::getPlayerScore)
                .sum();

        avgScore = sessions.isEmpty() ? 0 : (float) totalPlayerScore / sessionsCount;
        logger.info("Total player score: {}, Average player score: {}", totalPlayerScore, avgScore);

        int totalOpponentScore = sessions.stream()
                .filter(session -> session.getOpponentScore() != null)
                .mapToInt(CompletedSession::getOpponentScore)
                .sum();

        avgOpponentScore = sessions.isEmpty() ? 0 : (float) totalOpponentScore / sessionsCount;
        logger.info("Total opponent score: {}, Average opponent score: {}", totalOpponentScore, avgOpponentScore);

        long winCount = sessions.stream()
                .filter(session -> session.getEndState() == GameEndState.WIN)
                .count();

        winRate = sessions.isEmpty() ? 0 : (float) winCount / sessionsCount;
        logger.info("Total wins: {}, Win rate: {}", winCount, winRate);

        long firstToGoCount = sessions.stream()
                .filter(CompletedSession::getWasFirstToGo)
                .count();

        avgStartingFirst = sessions.isEmpty() ? 0 : (float) firstToGoCount / sessionsCount;
        logger.info("Total first-to-go count: {}, Average starting first: {}", firstToGoCount, avgStartingFirst);

        double totalSecondsForTurnAvg = sessions.stream()
                .filter(session -> session.getAvgSecondsPerTurn() != null)
                .mapToDouble(CompletedSession::getAvgSecondsPerTurn)
                .sum();

        avgSecondsPerTurn = sessions.isEmpty() ? 0 : (float) totalSecondsForTurnAvg / sessionsCount;
        logger.info("Total seconds per turn: {}, Average seconds per turn: {}", totalSessionDuration, avgSecondsPerTurn);

        double totalClicks = sessions.stream()
                .filter(session -> session.getClicks() != null)
                .mapToDouble(CompletedSession::getClicks)
                .sum();

        avgClicks = sessions.isEmpty() ? 0 : (float) totalClicks / sessionsCount;
        logger.info("Total clicks: {}, Average clicks: {}", totalSessionDuration, avgClicks);

        AggregatedPlayerSessionsData result = new AggregatedPlayerSessionsData(
                playerId,
                sessionsCount,
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

    private WinPredictionModeInputFeaturesDto calculateModelInputFeaturesDto(
            UUID playerOne,
            AggregatedPlayerSessionsData playerOneFeatures,
            UUID playerTwo,
            AggregatedPlayerSessionsData playerTwoFeatures
    ){

        float diffSessionCount = playerOneFeatures.getSessionCount() - playerTwoFeatures.getSessionCount();
        float diffWinRate = playerOneFeatures.getWin_rate() - playerTwoFeatures.getWin_rate();
        float diffAvgScore = playerOneFeatures.getAvg_score() - playerTwoFeatures.getAvg_score();
        float diffAvgTurnsTaken = playerOneFeatures.getAvg_turns_taken() - playerTwoFeatures.getAvg_turns_taken();
        float diffAvgSessionDuration = playerOneFeatures.getAvg_session_duration() - playerTwoFeatures.getAvg_session_duration();
        float diffAvgSecondsPerTurn = playerOneFeatures.getAvg_seconds_per_turn() - playerTwoFeatures.getAvg_seconds_per_turn();
        float diffAvgClicks = playerOneFeatures.getAvg_clicks() - playerTwoFeatures.getAvg_clicks();
        float diffAvgOpponentScore = playerOneFeatures.getAvg_opponent_score() - playerTwoFeatures.getAvg_opponent_score();
        float diffAvgStartingFirst = playerOneFeatures.getAvg_starting_first() - playerTwoFeatures.getAvg_starting_first();

        // Return a DTO with the feature differences
        return new WinPredictionModeInputFeaturesDto(
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
}
