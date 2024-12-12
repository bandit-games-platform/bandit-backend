package be.kdg.int5.statistics.utils;

import be.kdg.int5.statistics.domain.AchievementProgress;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.List;
import java.util.UUID;

@Component
public class CSVGeneratorUtil {
    private static final String[] CSV_HEADER_COMPLETED_SESSIONS = {
            "session_id", "start_time", "end_time", "end_state",
            "turns_taken", "avg_seconds_per_turn", "player_score",
            "opponent_score", "clicks", "character", "was_first_to_go",
            "game_id", "player_id"
    };
    private static final String[] CSV_HEADER_ACHIEVEMENT_PROGRESS = {
            "achievement_id", "counter_total",
            "game_id", "player_id"
    };

    public String convertSessionsToCsv(List<PlayerGameStats> playerGameStatsList) {
        List<String[]> csvData = playerGameStatsList.stream()
                .flatMap(playerGameStats -> playerGameStats.getCompletedSessions().stream()
                        .map(session -> new String[]{
                                session.getSessionId().toString(),
                                session.getStartTime().toString(),
                                session.getEndTime().toString(),
                                session.getEndState().name(),
                                String.valueOf(session.getTurnsTaken()),
                                String.valueOf(session.getAvgSecondsPerTurn()),
                                String.valueOf(session.getPlayerScore()),
                                String.valueOf(session.getOpponentScore()),
                                String.valueOf(session.getClicks()),
                                session.getCharacter(),
                                String.valueOf(session.getWasFirstToGo()),
                                String.valueOf(playerGameStats.getGameId()),
                                String.valueOf(playerGameStats.getPlayerId())
                        })
                )
                .toList();

        return convertToCsv(CSV_HEADER_COMPLETED_SESSIONS, csvData);
    }

    public String convertAchievementProgressListToCsv(List<PlayerGameStats> playerGameStatsList) {
        List<String[]> csvData = playerGameStatsList.stream()
                .flatMap(playerGameStats -> playerGameStats.getAchievementProgressSet().stream()
                        .map(ap -> new String[]{
                                ap.getAchievementId().toString(),
                                String.valueOf(ap.getCounterValue()),
                                String.valueOf(playerGameStats.getGameId()),
                                String.valueOf(playerGameStats.getPlayerId())
                        })
                )
                .toList();

        return convertToCsv(CSV_HEADER_ACHIEVEMENT_PROGRESS, csvData);
    }

    public static String convertToCsv(String[] csvHeader, List<String[]> data) {
        try (StringWriter writer = new StringWriter();
             CSVWriter csvWriter = new CSVWriter(writer)) {

            if (csvHeader != null && csvHeader.length > 0) {
                csvWriter.writeNext(csvHeader);
            }

            csvWriter.writeAll(data);

            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert data to CSV", e);
        }
    }

    public String convertAchievementProgressListToCsv2(List<AchievementProgress> achievementProgressList, UUID gameId) {
        List<String[]> csvData = achievementProgressList.stream()
                .map(ap -> new String[]{
                        ap.getAchievementId().toString(),
                        String.valueOf(ap.getCounterValue()),
                        String.valueOf(gameId),
                })
                .toList();

        return convertToCsv(CSV_HEADER_ACHIEVEMENT_PROGRESS, csvData);
    }
}
