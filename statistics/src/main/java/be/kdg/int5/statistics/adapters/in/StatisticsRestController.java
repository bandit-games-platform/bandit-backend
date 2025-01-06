package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.AchievementProgressDto;
import be.kdg.int5.statistics.adapters.in.dto.CompletedSessionDto;
import be.kdg.int5.statistics.adapters.in.dto.PlayerGameStatsDto;
import be.kdg.int5.statistics.adapters.in.dto.PlayerIdDto;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.query.GetAllUniquePlayerStatsForGameCommand;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.GetUniquePlayerStatsForGameQuery;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class StatisticsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final PlayerGameStatsQuery playerGameStatsQuery;
    private final GetUniquePlayerStatsForGameQuery getUniquePlayerStatsForGameQuery;

    public StatisticsRestController(PlayerGameStatsQuery playerGameStatsQuery, GetUniquePlayerStatsForGameQuery getUniquePlayerStatsForGameQuery) {
        this.playerGameStatsQuery = playerGameStatsQuery;
        this.getUniquePlayerStatsForGameQuery = getUniquePlayerStatsForGameQuery;
    }

    @GetMapping("/player-game-statistics/{playerId}/{gameId}")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<PlayerGameStatsDto> getPlayerGameStats(
            @NotNull @PathVariable("playerId") UUID playerId,
            @NotNull @PathVariable("gameId") UUID gameId
    ) {

        PlayerGameStats playerGameStats = playerGameStatsQuery.getPlayerGameStats(
                new GetPlayerGameStatsCommand(
                        new PlayerId(playerId),
                        new GameId(gameId)
                )
        );

        if (playerGameStats != null) {
            PlayerGameStatsDto statsDTO = mapToDTO(playerGameStats);
            return ResponseEntity.ok(statsDTO);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/player-game-statistics/games/{gameId}/completed-sessions/players")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Set<PlayerIdDto>> getAllPlayersWhichCompletedSessionsForGame(
            @NotNull @PathVariable("gameId") UUID gameId
    ) {

        Set<PlayerGameStats> playerGameStats = getUniquePlayerStatsForGameQuery.getAllUniquePlayerStatsForGame(
                new GetAllUniquePlayerStatsForGameCommand(new GameId(gameId))
        );

        if (playerGameStats != null && !playerGameStats.isEmpty()) {
            Set<PlayerIdDto> playerIdDtos = playerGameStats.stream()
                    .map(stats -> new PlayerIdDto(stats.getPlayerId().uuid()))

                    .collect(Collectors.toSet());
            return ResponseEntity.ok(playerIdDtos);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private PlayerGameStatsDto mapToDTO(PlayerGameStats playerGameStats) {
        // Map CompletedSession to CompletedSessionDto
        List<CompletedSessionDto> sessionDTOs = playerGameStats.getCompletedSessions().stream()
                .map(session -> new CompletedSessionDto(
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

        // Map AchievementProgress to AchievementProgressDto
        List<AchievementProgressDto> achievementDTOs = playerGameStats.getAchievementProgressSet().stream()
                .map(progress -> new AchievementProgressDto(
                        progress.getAchievementId().uuid(),
                        progress.getCounterValue()
                ))
                .toList();

        // Create the PlayerGameStatsDto
        return new PlayerGameStatsDto(
                playerGameStats.getPlayerId().uuid(),
                playerGameStats.getGameId().uuid(),
                sessionDTOs,
                achievementDTOs
        );
    }
}

