package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.NewAchievementProgressDto;
import be.kdg.int5.statistics.adapters.in.dto.NewCompletedSessionDto;
import be.kdg.int5.statistics.domain.AchievementId;
import be.kdg.int5.statistics.domain.GameEndState;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SubmitStatisticsRestController {
    private final SaveCompletedSessionUseCase saveCompletedSessionUseCase;
    private final VerifyIfDeveloperOwnsGameUseCase verifyIfDeveloperOwnsGameUseCase;
    private final UpdateAchievementProgressUseCase updateAchievementProgressUseCase;
    private final VerifyIfAchievementBelongsToGameUseCase verifyIfAchievementBelongsToGameUseCase;

    public SubmitStatisticsRestController(SaveCompletedSessionUseCase saveCompletedSessionUseCase, VerifyIfDeveloperOwnsGameUseCase verifyIfDeveloperOwnsGameUseCase, UpdateAchievementProgressUseCase updateAchievementProgressUseCase, VerifyIfAchievementBelongsToGameUseCase verifyIfAchievementBelongsToGameUseCase) {
        this.saveCompletedSessionUseCase = saveCompletedSessionUseCase;
        this.verifyIfDeveloperOwnsGameUseCase = verifyIfDeveloperOwnsGameUseCase;
        this.updateAchievementProgressUseCase = updateAchievementProgressUseCase;
        this.verifyIfAchievementBelongsToGameUseCase = verifyIfAchievementBelongsToGameUseCase;
    }

    @PostMapping("/statistics/submit")
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity<Void> submitNewGameStatistics(
            @RequestParam String gameId,
            @RequestParam String playerId,
            @Valid @RequestBody NewCompletedSessionDto dto,
            @AuthenticationPrincipal Jwt token
    ) {

        UUID developerId = UUID.fromString(token.getClaimAsString("sub"));

        boolean ownsGame = verifyIfDeveloperOwnsGameUseCase.doesDeveloperOwnGame(new VerifyIfDeveloperOwnsGameCommand(
                developerId,
                new GameId(UUID.fromString(gameId))
        ));

        if (!ownsGame) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        saveCompletedSessionUseCase.saveCompletedGameSession(new SaveCompletedSessionCommand(
                new PlayerId(UUID.fromString(playerId)),
                new GameId(UUID.fromString(gameId)),
                dto.getStartTime(),
                dto.getEndTime(),
                GameEndState.valueOf(dto.getEndState()),
                dto.getTurnsTaken(),
                dto.getAvgSecondsPerTurn(),
                dto.getPlayerScore(),
                dto.getOpponentScore(),
                dto.getClicks(),
                dto.getCharacter(),
                dto.getWasFirstToGo()
        ));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/statistics/achievements/{achievementId}")
    @PreAuthorize("hasAuthority('developer')")
    public ResponseEntity<Void> updateAchievementProgress(
            @PathVariable String achievementId,
            @RequestParam String gameId,
            @RequestParam String playerId,
            @Valid @RequestBody NewAchievementProgressDto dto,
            @AuthenticationPrincipal Jwt token
    ) {
        UUID developerId = UUID.fromString(token.getClaimAsString("sub"));
        GameId gameIdObj = new GameId(UUID.fromString(gameId));
        boolean ownsGame = verifyIfDeveloperOwnsGameUseCase.doesDeveloperOwnGame(new VerifyIfDeveloperOwnsGameCommand(
                developerId, gameIdObj)
        );
        if (!ownsGame) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        boolean belongsToGame = verifyIfAchievementBelongsToGameUseCase.doesAchievementBelongToGame(
                new VerifyIfAchievementBelongsToGameCommand(gameIdObj, new AchievementId(UUID.fromString(achievementId)))
        );
        if (!belongsToGame) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        updateAchievementProgressUseCase.updateAchievementProgress(
                new UpdateAchievementProgressCommand(
                        new PlayerId(UUID.fromString(playerId)),
                        new GameId(UUID.fromString(gameId)),
                        new AchievementId(UUID.fromString(achievementId)),
                        dto.getNewProgressAmount() != null ? dto.getNewProgressAmount() : null
                )
        );

        return ResponseEntity.ok().build();
    }
}
