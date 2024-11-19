package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.AchievementDTO;
import be.kdg.int5.statistics.adapters.in.dto.AchievementProgressDTO;
import be.kdg.int5.statistics.adapters.in.dto.CompletedSessionDTO;
import be.kdg.int5.statistics.adapters.in.dto.PlayerGameStatsDTO;
import be.kdg.int5.statistics.domain.Achievement;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.query.GetGameAchievementCommand;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class StatisticsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final PlayerGameStatsQuery playerGameStatsQuery;

    public StatisticsRestController(PlayerGameStatsQuery playerGameStatsQuery) {
        this.playerGameStatsQuery = playerGameStatsQuery;
    }


    @GetMapping("/player-game-statistics/{playerId}/{gameId}")
    public ResponseEntity<PlayerGameStatsDTO> getPlayerGameStats(
            @PathVariable("playerId") UUID playerId,
            @PathVariable("gameId") UUID gameId
    ) {
        // Fetch the PlayerGameStats domain object
        PlayerGameStats playerGameStats = playerGameStatsQuery.getPlayerGameStats(
                new GetPlayerGameStatsCommand(
                        new PlayerId(playerId),
                        new GameId(gameId)
                )
        );

        // Map the domain model to the DTO
        PlayerGameStatsDTO statsDTO = mapToDTO(playerGameStats);

        // Return the DTO wrapped in a ResponseEntity
        return ResponseEntity.ok(statsDTO);
    }

    @GetMapping("/game-achievements/{gameId}")
    public ResponseEntity<List<AchievementDTO>> getGameAchievement(
            @PathVariable("gameId") UUID gameId
    ) {
        // Fetch the PlayerGameStats domain object
        List<Achievement> achievements = playerGameStatsQuery.getGameAchievement(
                new GetGameAchievementCommand(new GameId(gameId))
        );

        List<AchievementDTO> achievementDTOs = achievements.stream()
                .map(achievement -> new AchievementDTO(
                        achievement.id().uuid(),
                        achievement.gameId().uuid(),
                        achievement.counterTotal(),
                        achievement.description(),
                        achievement.title()
                ))
                .collect(Collectors.toList());

        // Return the DTOs wrapped in a ResponseEntity
        return ResponseEntity.ok(achievementDTOs);
    }

    private PlayerGameStatsDTO mapToDTO(PlayerGameStats playerGameStats) {
        // Map CompletedSession to CompletedSessionDTO
        List<CompletedSessionDTO> sessionDTOs = playerGameStats.getCompletedSessions().stream()
                .map(session -> new CompletedSessionDTO(
                        session.getSessionId().uuid(),
                        session.getStartTime(),
                        session.getEndTime(),
                        session.getEndState().toString(),
                        session.getTurnsTaken(),
                        session.getAvgSecondsPerTurn(),
                        session.getPlayerScore(),
                        session.getOpponentScore(),
                        session.getClicks(),
                        session.getCharacter(),
                        session.getWasFirstToGo()
                ))
                .toList();

        // Map AchievementProgress to AchievementProgressDTO
        List<AchievementProgressDTO> achievementDTOs = playerGameStats.getAchievementProgressSet().stream()
                .map(progress -> new AchievementProgressDTO(
                        progress.getAchievementId().uuid(),
                        progress.getCounterValue()
                ))
                .toList();

        // Create the PlayerGameStatsDTO
        return new PlayerGameStatsDTO(
                playerGameStats.getPlayerId().uuid(),
                playerGameStats.getGameId().uuid(),
                sessionDTOs,
                achievementDTOs
        );
    }


}

