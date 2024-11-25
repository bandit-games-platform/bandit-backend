package be.kdg.int5.gameRegistry.adapters.in;


import be.kdg.int5.gameRegistry.adapters.in.dto.AchievementGameDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.DeveloperDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.LoadAchievementIdDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.LoadDeveloperIdDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.LoadGameDto;
import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GetGameAchievementsQuery;
import jakarta.validation.Valid;
import be.kdg.int5.gameRegistry.port.in.query.GameDetailsQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameDetailsRestController {
    private final GameDetailsQuery gameDetailsQuery;
    private final GetGameAchievementsQuery getGameAchievementsQuery;

    public GameDetailsRestController(GameDetailsQuery gameDetailsQuery, GetGameAchievementsQuery getGameAchievementsQuery) {
        this.gameDetailsQuery = gameDetailsQuery;
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
}
