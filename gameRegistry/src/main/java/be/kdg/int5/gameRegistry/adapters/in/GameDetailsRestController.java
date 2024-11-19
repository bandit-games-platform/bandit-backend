package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.gameRegistry.adapters.in.dto.DeveloperDto;
import be.kdg.int5.gameRegistry.adapters.in.dto.LoadGameDto;
import be.kdg.int5.gameRegistry.domain.Game;
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

@RestController
@RequestMapping("/games")
public class GameDetailsRestController {
    private final GameDetailsQuery gameDetailsQuery;

    public GameDetailsRestController(GameDetailsQuery gameDetailsQuery) {
        this.gameDetailsQuery = gameDetailsQuery;
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
}
