package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.PlayerGameStatsDTO;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.query.GetPlayerGameStatsCommand;
import be.kdg.int5.statistics.port.in.query.PlayerGameStatsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class StatisticsRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsRestController.class);
    private final PlayerGameStatsQuery playerGameStatsQuery;

    public StatisticsRestController(PlayerGameStatsQuery playerGameStatsQuery) {
        this.playerGameStatsQuery = playerGameStatsQuery;
    }

    @GetMapping("/player-game-statistics/{playerId}/{gameId}")
    ResponseEntity<PlayerGameStatsDTO> getPlayerGameStats(
            @PathVariable("playerId") UUID playerId,
            @PathVariable("gameId") UUID gameId
    ){
       PlayerGameStatsDTO statsDTO = new PlayerGameStatsDTO(playerId, gameId);
        PlayerGameStats playerGameStats = playerGameStatsQuery.getPlayerGameStats(
                new GetPlayerGameStatsCommand(
                        new PlayerId(statsDTO.getPlayerId()),
                        new GameId(statsDTO.getGameId()))
        );
        return null;
    }

}
