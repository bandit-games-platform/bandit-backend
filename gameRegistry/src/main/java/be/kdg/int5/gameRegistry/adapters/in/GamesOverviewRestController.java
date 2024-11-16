package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.gameRegistry.adapters.in.dto.LoadGameDto;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesOverviewRestController {

    private final GameListQuery gameListQuery;

    public GamesOverviewRestController(GameListQuery gameListQuery) {
        this.gameListQuery = gameListQuery;
    }

    @GetMapping("/overview")
    public ResponseEntity<List<LoadGameDto>> getGameOverview(@RequestParam(value = "title", required = false) String title) {

        List<Game> games;
        List<LoadGameDto> gameDtos = new ArrayList<>();


        if (title != null && !title.isEmpty()) {
            games = gameListQuery.retrieveGamesByTitleLikeWithIcon(title);
        } else {
            games = gameListQuery.retrieveGamesWithIcon();
        }

        games.forEach(game -> {
            LoadGameDto gameDto = new LoadGameDto(
                    game.getTitle(),
                    game.getDescription(),
                    game.getIcon().url().url(),
                    game.getId().uuid()
            );
            gameDtos.add(gameDto);
        });

        return ResponseEntity.ok(gameDtos);
    }
}
