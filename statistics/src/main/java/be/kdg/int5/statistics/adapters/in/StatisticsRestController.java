package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.AchievementProgressDTO;
import be.kdg.int5.statistics.adapters.in.dto.CompletedSessionDTO;
import be.kdg.int5.statistics.adapters.in.dto.PlayerGameStatsDTO;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

@RestController
public class StatisticsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final PlayerGameStatsQuery playerGameStatsQuery;

    public StatisticsRestController(PlayerGameStatsQuery playerGameStatsQuery) {
        this.playerGameStatsQuery = playerGameStatsQuery;
    }

    @GetMapping("/player-game-statistics/{playerId}/{gameId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<PlayerGameStatsDTO> getPlayerGameStats(
            @NotNull @PathVariable("playerId") UUID playerId,
            @NotNull @PathVariable("gameId") UUID gameId
    ) {

        PlayerGameStats playerGameStats = playerGameStatsQuery.getPlayerGameStats(
                new GetPlayerGameStatsCommand(
                        new PlayerId(playerId),
                        new GameId(gameId)
                )
        );

        if (playerGameStats != null){
            PlayerGameStatsDTO statsDTO = mapToDTO(playerGameStats);
            return ResponseEntity.ok(statsDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

