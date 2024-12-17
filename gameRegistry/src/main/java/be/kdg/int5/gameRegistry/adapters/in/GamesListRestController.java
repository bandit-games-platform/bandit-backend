package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.gameRegistry.adapters.in.dto.LoadGameDto;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesListRestController {
    private final GameListQuery gameListQuery;
    public GamesListRestController(GameListQuery gameListQuery) {
        this.gameListQuery = gameListQuery;
    }

    @GetMapping()
    public ResponseEntity<List<LoadGameDto>> getAllGames() {
        List<Game> games;
        List<LoadGameDto> gameDtos = new ArrayList<>();

        games = gameListQuery.retrieveGamesWithIcon();

        games.forEach(game -> {
            LoadGameDto gameDto = new LoadGameDto(
                    game.getId().uuid(),
                    game.getTitle(),
                    game.getIcon().url().url()
            );
            gameDtos.add(gameDto);
        });

        return ResponseEntity.ok(gameDtos);
    }
}
