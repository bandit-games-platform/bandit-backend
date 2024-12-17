package be.kdg.int5.statistics.utils;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CSVGeneratorUtil {
    private static final String[] CSV_HEADER_COMPLETED_SESSIONS = {
            "game_id", "player_id", "session_id", "start_time", "end_time", "end_state",
            "turns_taken", "avg_seconds_per_turn", "player_score",
            "opponent_score", "clicks", "character", "was_first_to_go"
    };

    private static final String[] CSV_HEADER_ACHIEVEMENT_PROGRESS = {
            "game_id", "player_id", "achievement_id", "counter_total"
    };

    public String convertSessionsToCsv(List<PlayerGameStats> playerGameStatsList) {
        StringBuilder csvBuilder = new StringBuilder();
        appendLine(csvBuilder, CSV_HEADER_COMPLETED_SESSIONS);

        playerGameStatsList.forEach(playerGameStats -> {
            playerGameStats.getCompletedSessions().forEach(session -> {
                appendLine(csvBuilder, new String[]{
                        playerGameStats.getGameId().uuid().toString(),
                        playerGameStats.getPlayerId().uuid().toString(),
                        session.getSessionId().uuid().toString(),
                        session.getStartTime().toString(),
                        session.getEndTime().toString(),
                        session.getEndState().name(),
                        String.valueOf(session.getTurnsTaken()),
                        String.valueOf(session.getAvgSecondsPerTurn()),
                        session.getPlayerScore() != null ? session.getPlayerScore().toString() : "",
                        session.getOpponentScore() != null ? session.getOpponentScore().toString() : "",
                        session.getClicks() != null ? session.getClicks().toString() : "",
                        session.getCharacter() != null ? session.getCharacter() : "",
                        String.valueOf(session.getWasFirstToGo())
                });
            });
        });
        return csvBuilder.toString();
    }

    public String convertAchievementProgressListToCsv(List<PlayerGameStats> playerGameStatsList) {
        StringBuilder csvBuilder = new StringBuilder();
        appendLine(csvBuilder, CSV_HEADER_ACHIEVEMENT_PROGRESS);

        playerGameStatsList.forEach(playerGameStats -> {
            playerGameStats.getAchievementProgressSet().forEach(ap -> {
                appendLine(csvBuilder, new String[]{
                        playerGameStats.getGameId().uuid().toString(),
                        playerGameStats.getPlayerId().uuid().toString(),
                        ap.getAchievementId().uuid().toString(),
                        String.valueOf(ap.getCounterValue())
                });
            });
        });
        return csvBuilder.toString();
    }

    private void appendLine(StringBuilder builder, String[] values) {
        builder.append(String.join(",", values)).append("\n");
    }
}
