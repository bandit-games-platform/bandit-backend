package be.kdg.int5.gameRegistry.adapters.in;

import be.kdg.int5.gameRegistry.adapters.in.dto.LoadGameDto;
import be.kdg.int5.gameRegistry.domain.Game;
import be.kdg.int5.gameRegistry.port.in.query.GameListQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesOverviewRestController {

    private final GameListQuery gameListQuery;

    public GamesOverviewRestController(GameListQuery gameListQuery) {
        this.gameListQuery = gameListQuery;
    }

    @GetMapping("/overview")
    public ResponseEntity<List<LoadGameDto>> getGameOverviewWithTitleAndPriceFilter(@RequestParam(value = "title", required = false) String title,
                                                                                    @RequestParam(value = "maxPrice", required = false) String maxPrice) {

        List<Game> games;
        List<LoadGameDto> gameDtos = new ArrayList<>();
        BigDecimal maxPriceMoney;

        if (maxPrice != null && !maxPrice.isEmpty()) {
            try {
                maxPriceMoney = new BigDecimal(maxPrice);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body(Collections.emptyList());
            }
        } else {
            maxPriceMoney = BigDecimal.valueOf(Double.MAX_VALUE);
        }


        if (title != null) {
            games = gameListQuery.retrieveGamesByTitleLikeAndPriceBelowWithIcon(title, maxPriceMoney);
        } else {
            games = gameListQuery.retrieveGamesWithIcon();
        }

        games.forEach(game -> {
            LoadGameDto gameDto = new LoadGameDto(
                    game.getId().uuid(),
                    game.getTitle(),
                    game.getDescription(),
                    game.getCurrentPrice(),
                    game.getIcon().url().url(),
                    game.getId().uuid()
            );
            gameDtos.add(gameDto);
        });

        return ResponseEntity.ok(gameDtos);
    }
}
