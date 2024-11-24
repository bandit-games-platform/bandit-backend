package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.gameRegistry.adapters.in.dto.AchievementDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.DeveloperDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.LoadGameDto;
import be.kdg.int5.gameRegistry.domain.Achievement;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GetGameAchievementsQuery;
import be.kdg.int5.gameRegistry.port.in.query.GetGameDetailsQuery;
import jakarta.validation.Valid;
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
    private final GetGameDetailsQuery getGameDetailsQuery;
    private final GetGameAchievementsQuery getGameAchievementsQuery;

    public GameDetailsRestController(GetGameDetailsQuery getGameDetailsQuery, GetGameAchievementsQuery getGameAchievementsQuery) {
        this.getGameDetailsQuery = getGameDetailsQuery;
        this.getGameAchievementsQuery = getGameAchievementsQuery;
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<LoadGameDto> getDetailsForGame(@PathVariable String gameId) {
        UUID gameIdConverted = UUID.fromString(gameId);

        Game game = getGameDetailsQuery.getDetailsForGameFromId(gameIdConverted);
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

    @GetMapping("/{gameId}/achievements")
    public ResponseEntity<List<AchievementDto>> getGameAchievement(@Valid @PathVariable("gameId") UUID gameId) {

        List<Achievement> achievements = getGameAchievementsQuery.getAchievementsForGameFromId(gameId);

        List<AchievementDto> achievementDTOs = achievements.stream()
                .map(achievement -> new AchievementDto(
                        achievement.getId().uuid(),
                        gameId,
                        achievement.getDescription(),
                        achievement.getCounterTotal(),
                        achievement.getTitle()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(achievementDTOs);
    }
}
