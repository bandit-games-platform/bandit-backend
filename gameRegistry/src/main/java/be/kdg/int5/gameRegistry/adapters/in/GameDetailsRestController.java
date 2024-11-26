package be.kdg.int5.gameRegistry.adapters.in;


import be.kdg.int5.gameRegistry.adapters.in.dto.*;
import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.domain.GameId;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import be.kdg.int5.gameRegistry.port.in.query.GetGameAchievementsQuery;
import jakarta.validation.Valid;
import be.kdg.int5.gameRegistry.port.in.query.GameDetailsQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameDetailsRestController {
    private final GameDetailsQuery gameDetailsQuery;
    private final GameListQuery gameListQuery;
    private final GetGameAchievementsQuery getGameAchievementsQuery;

    public GameDetailsRestController(GameDetailsQuery gameDetailsQuery, GameListQuery gameListQuery, GetGameAchievementsQuery getGameAchievementsQuery) {
        this.gameDetailsQuery = gameDetailsQuery;
        this.gameListQuery = gameListQuery;
        this.getGameAchievementsQuery = getGameAchievementsQuery;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<LoadGameDto> getDetailsForGame(@PathVariable String gameId) {
        UUID gameIdConverted = UUID.fromString(gameId);

        Game game = gameDetailsQuery.getDetailsForGameFromId(gameIdConverted);
        if (game == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<String> screenshots = new ArrayList<>();
        game.getScreenshots().forEach(screenshot -> screenshots.add(screenshot.url().url()));

        LoadGameDto loadedGame = new LoadGameDto(
                game.getId().uuid(),
                game.getTitle(),
                game.getDescription(),
                game.getCurrentPrice(),
                game.getBackground().url().url(),
                new DeveloperDto(game.getDeveloper().studioName()),
                screenshots
        );

        return ResponseEntity.ok(loadedGame);
    }

    @GetMapping("/{gameId}/developer")
    public ResponseEntity<LoadDeveloperIdDto> getDeveloperThatOwnsGame(@PathVariable String gameId) {
        UUID gameIdConverted = UUID.fromString(gameId);

        Game game = gameDetailsQuery.getGameWithoutRelationshipsFromId(gameIdConverted);
        if (game == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(new LoadDeveloperIdDto(game.getDeveloper().id().uuid()));
    }

    @GetMapping("/{gameId}/achievement-ids")
    public ResponseEntity<List<LoadAchievementIdDto>> getAchievementIdsForGame(@PathVariable String gameId) {
        UUID gameIdConverted = UUID.fromString(gameId);

        Game game = gameDetailsQuery.getGameWithAchievementsFromId(gameIdConverted);
        if (game == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        List<LoadAchievementIdDto> achievementIdDtos = game.getAchievements().stream().map(
                achievement -> new LoadAchievementIdDto(achievement.getId().uuid())
        ).collect(Collectors.toList());
        return ResponseEntity.ok(achievementIdDtos);
    }

    @GetMapping("/{gameId}/achievements")
    public ResponseEntity<List<AchievementGameDto>> getGameAchievement(@Valid @PathVariable("gameId") UUID gameId) {
        List<Achievement> achievements = getGameAchievementsQuery.getAchievementsForGameFromId(gameId);

        List<AchievementGameDto> achievementDtoList = achievements.stream()
                .map(achievement -> new AchievementGameDto(
                        achievement.getId().uuid(),
                        gameId,
                        achievement.getDescription(),
                        achievement.getCounterTotal(),
                        achievement.getTitle()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(achievementDtoList);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('player')")
    public ResponseEntity<List<LoadGameDto>> getGameInformationFromListOfIds(
            @RequestBody List<GetGameDto> gameIds
    ) {
        List<GameId> gameIdList = gameIds.stream().map(
                gameId -> new GameId(gameId.getGameId())
        ).collect(Collectors.toList());

        List<Game> games = gameListQuery.getGamesByIdInList(gameIdList);

        if (games.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<LoadGameDto> loadGameDtoList = games.stream().map(
                game -> new LoadGameDto(game.getId().uuid(),
                        game.getTitle(),
                        game.getDescription(),
                        game.getIcon().url().url(),
                        game.getBackground().url().url(),
                        null,
                        game.getCurrentHost().url(),
                        null,
                        game.getAchievements().stream().map(
                                achievement -> new LoadAchievementDto(
                                        achievement.getId().uuid(),
                                        achievement.getTitle(),
                                        achievement.getCounterTotal(),
                                        achievement.getDescription()
                                )
                        ).collect(Collectors.toSet()))
        ).collect(Collectors.toList());

        return ResponseEntity.ok(loadGameDtoList);
    }
}
