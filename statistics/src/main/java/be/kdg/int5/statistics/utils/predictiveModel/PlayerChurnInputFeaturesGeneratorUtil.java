package be.kdg.int5.statistics.utils.predictiveModel;

import be.kdg.int5.statistics.domain.CompletedSession;
import be.kdg.int5.statistics.domain.GameEndState;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.Duration;

@Component
public class PlayerChurnInputFeaturesGeneratorUtil {

    public PlayerChurnInputFeaturesDto convertToInputFeatures(List<PlayerGameStats> playerGameStats){

        int sessionCount = 0;
        int gameVariety = 0;
        float totalSessionDuration = 0;
        float totalPlayerScore = 0;
        float totalOpponentScore = 0;
        int winCount = 0;
        LocalDateTime lastSessionEndTime = null;

        // Unique games played
        Set<UUID> gamesPlayed = playerGameStats.stream()
                .map(pgs -> pgs.getGameId().uuid())
                .collect(Collectors.toSet());

        gameVariety = gamesPlayed.size();

        // Aggregated data
        for (PlayerGameStats stats : playerGameStats) {
            for (CompletedSession session : stats.getCompletedSessions()) {
                sessionCount++;
                totalSessionDuration += session.getPlayDurationInSeconds();
                totalPlayerScore += session.getPlayerScore();
                totalOpponentScore += session.getOpponentScore();
                if (session.getEndState() == GameEndState.WIN) {
                    winCount++;
                }
                if (lastSessionEndTime == null || session.getEndTime().isAfter(lastSessionEndTime)) {
                    lastSessionEndTime = session.getEndTime();
                }
            }
        }

        // Feature values
        float avgSessionDuration = sessionCount > 0 ? totalSessionDuration / sessionCount : 0;
        float avgPlayerScore = sessionCount > 0 ? totalPlayerScore / sessionCount : 0;
        float avgOpponentScore = sessionCount > 0 ? totalOpponentScore / sessionCount : 0;
        float winRate = sessionCount > 0 ? (float) winCount / sessionCount : 0;
        int daysSinceLastSession = lastSessionEndTime != null ? (int) Duration.between(lastSessionEndTime, LocalDateTime.now()).toDays() : 0;

//        PlayerChurnInputFeaturesDto playerChurnInputFeaturesDto = new PlayerChurnInputFeaturesDto(
//                playerGameStats.getGender(),
//                playerGameStats.getCountry(),
//                playerGameStats.getAge(),
//                sessionCount,
//                gameVariety,
//                avgSessionDuration,
//                avgPlayerScore,
//                avgOpponentScore,
//                winRate,
//                daysSinceLastSession
//        );

//        return playerChurnInputFeaturesDto;
        return null;
    }
}
