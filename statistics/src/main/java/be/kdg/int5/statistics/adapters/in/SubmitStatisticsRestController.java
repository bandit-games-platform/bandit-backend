package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.adapters.in.dto.NewCompletedSessionDto;
import be.kdg.int5.statistics.domain.GameEndState;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionCommand;
import be.kdg.int5.statistics.port.in.SaveCompletedSessionUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SubmitStatisticsRestController {
    private final SaveCompletedSessionUseCase saveCompletedSessionUseCase;

    public SubmitStatisticsRestController(SaveCompletedSessionUseCase saveCompletedSessionUseCase) {
        this.saveCompletedSessionUseCase = saveCompletedSessionUseCase;
    }

    @PostMapping("/statistics/submit")
    @PreAuthorize("hasAuthority('developer')")
    public void submitNewGameStatistics(
            @RequestParam String gameId,
            @RequestParam String playerId,
            @RequestBody NewCompletedSessionDto dto) {

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
    }
}
