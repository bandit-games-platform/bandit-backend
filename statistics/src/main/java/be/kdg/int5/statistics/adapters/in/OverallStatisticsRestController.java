package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.AchievementProgressDto;
import be.kdg.int5.statistics.adapters.in.dto.CompletedSessionDto;
import be.kdg.int5.statistics.adapters.in.dto.GameProgressDto;
import be.kdg.int5.statistics.domain.*;
import be.kdg.int5.statistics.port.in.query.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class OverallStatisticsRestController {
    private final PlayerTotalPlayTimeQuery playerTotalPlayTimeQuery;
    private final PlayerAchievementProgressForPlayedGamesQuery playerAchievementProgressForPlayedGamesQuery;
    private final StatisticsForGameQuery statisticsForGameQuery;

    public OverallStatisticsRestController(
            PlayerTotalPlayTimeQuery playerTotalPlayTimeQuery,
            PlayerAchievementProgressForPlayedGamesQuery playerAchievementProgressForPlayedGamesQuery, StatisticsForGameQuery statisticsForGameQuery
    ) {
        this.playerTotalPlayTimeQuery = playerTotalPlayTimeQuery;
        this.playerAchievementProgressForPlayedGamesQuery = playerAchievementProgressForPlayedGamesQuery;
        this.statisticsForGameQuery = statisticsForGameQuery;
    }

    @GetMapping("/playtime")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<Long> getTotalPlaytime(
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        long playTime = playerTotalPlayTimeQuery.getTotalPlayTimeSecondsForPlayer(new PlayerTotalPlayTimeCommand(
                new PlayerId(playerId)
        ));
        if (playTime == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(playTime);
    }

    @GetMapping("/games/progress")
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<GameProgressDto>> getAllGameProgress(
            @AuthenticationPrincipal Jwt token
    ) {
        UUID playerId = UUID.fromString(token.getClaimAsString("sub"));
        List<PlayerGameStats> playerGameStats = playerAchievementProgressForPlayedGamesQuery.getAllPlayerAchievementProgressForGamesTheyPlayed(
                new PlayerAchievementProgressForPlayedGamesCommand(new PlayerId(playerId))
        );
        if (playerGameStats == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<GameProgressDto> gameProgressDtos = playerGameStats.stream().map(
                playerGameStat -> new GameProgressDto(
                        playerGameStat.getGameId().uuid(),
                        playerGameStat.getCompletedSessions().stream().filter(
                                completedSession -> completedSession.getEndState() == GameEndState.WIN
                        ).count(),
                        playerGameStat.getCompletedSessions().stream().filter(
                                completedSession -> completedSession.getEndState() == GameEndState.LOSS
                        ).count(),
                        playerGameStat.getCompletedSessions().stream().filter(
                                completedSession -> completedSession.getEndState() == GameEndState.DRAW
                        ).count(),
                        playerGameStat.getAchievementProgressSet().stream().map(
                                achievementProgress -> new AchievementProgressDto(
                                        achievementProgress.getAchievementId().uuid(),
                                        achievementProgress.getCounterValue()
                                )
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
        return ResponseEntity.ok(gameProgressDtos);
    }

    @GetMapping("/games/{gameId}/completedSessions")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<CompletedSessionDto>> getAllGameSessionsByGame(
            @NotNull @PathVariable("gameId") UUID gameId
    ) {
        List<CompletedSession> completedSessions = statisticsForGameQuery.getAllCompletedSessionsForGame(
                new GetStatisticsForGameCommand(new GameId(gameId))
        );

        if (completedSessions == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

        List<CompletedSessionDto> completedSessionDtos = completedSessions.stream().map(
                completedSession -> new CompletedSessionDto(
                        completedSession.getSessionId().uuid(),
                        completedSession.getStartTime(),
                        completedSession.getEndTime(),
                        completedSession.getEndState().name(),
                        completedSession.getTurnsTaken(),
                        completedSession.getAvgSecondsPerTurn(),
                        completedSession.getPlayerScore(),
                        completedSession.getOpponentScore(),
                        completedSession.getClicks(),
                        completedSession.getCharacter(),
                        completedSession.getWasFirstToGo()
                )
        ).toList();
        return ResponseEntity.ok(completedSessionDtos);
    }

    @GetMapping("/games/{gameId}/achievementProgress")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<AchievementProgressDto>> getAllAchievementProgressByGame(
            @NotNull @PathVariable("gameId") UUID gameId
    ) {
        List<AchievementProgress> achievementProgressList = statisticsForGameQuery.getAllAchievementProgressForGame(
                new GetStatisticsForGameCommand(new GameId(gameId))
        );

        if (achievementProgressList == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<AchievementProgressDto> achievementProgressDtos = achievementProgressList.stream().map(
                ap -> new AchievementProgressDto(
                        ap.getAchievementId().uuid(),
                        ap.getCounterValue()
                )
        ).toList();
        return ResponseEntity.ok(achievementProgressDtos);
    }
}
