package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.AchievementProgressDTO;
import be.kdg.int5.statistics.adapters.in.dto.GameProgressDto;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.query.PlayerAchievementProgressForPlayedGamesCommand;
import be.kdg.int5.statistics.port.in.query.PlayerAchievementProgressForPlayedGamesQuery;
import be.kdg.int5.statistics.port.in.query.PlayerTotalPlayTimeCommand;
import be.kdg.int5.statistics.port.in.query.PlayerTotalPlayTimeQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
public class OverallStatisticsRestController {
    private final PlayerTotalPlayTimeQuery playerTotalPlayTimeQuery;
    private final PlayerAchievementProgressForPlayedGamesQuery playerAchievementProgressForPlayedGamesQuery;

    public OverallStatisticsRestController(
            PlayerTotalPlayTimeQuery playerTotalPlayTimeQuery,
            PlayerAchievementProgressForPlayedGamesQuery playerAchievementProgressForPlayedGamesQuery
    ) {
        this.playerTotalPlayTimeQuery = playerTotalPlayTimeQuery;
        this.playerAchievementProgressForPlayedGamesQuery = playerAchievementProgressForPlayedGamesQuery;
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
                        playerGameStat.getAchievementProgressSet().stream().map(
                                achievementProgress -> new AchievementProgressDTO(
                                        achievementProgress.getAchievementId().uuid(),
                                        achievementProgress.getCounterValue()
                                )
                        ).collect(Collectors.toList())
                )
        ).collect(Collectors.toList());
        return ResponseEntity.ok(gameProgressDtos);
    }
}
