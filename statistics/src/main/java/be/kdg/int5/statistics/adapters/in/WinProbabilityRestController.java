package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.statistics.adapters.in.dto.WinPredictionDto;
import be.kdg.int5.statistics.adapters.in.dto.WinProbabilityRequestDto;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerId;
import be.kdg.int5.statistics.port.in.*;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class WinProbabilityRestController {
    private static final Logger logger = LoggerFactory.getLogger(WinProbabilityRestController.class);
    private final WinProbabilityUseCase winProbabilityUseCase;

    public WinProbabilityRestController(WinProbabilityUseCase winProbabilityUseCase) {
        this.winProbabilityUseCase = winProbabilityUseCase;
    }

    @PostMapping("/statistics/games/{gameId}/win-probability/predict")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<WinPredictionDto> predictWinProbability(
            @NotNull @PathVariable("gameId") UUID gameId,
            @NotNull @RequestBody WinProbabilityRequestDto dto) {
        try {
            WinProbabilityCommand command = new WinProbabilityCommand(
                    new GameId(gameId),
                    new PlayerId(dto.getPlayerOneId()),
                    new PlayerId(dto.getPlayerTwoId())
            );

            WinPrediction response = winProbabilityUseCase.predictWinProbability(command);

            WinPredictionDto winPredictionDto = new WinPredictionDto(
                    response.getPlayerOneId(),
                    response.getWinProbabilityPlayerOne(),
                    response.getPlayerTwoId(),
                    response.getWinProbabilityPlayerTwo()
            );
            return ResponseEntity.ok(winPredictionDto);
        } catch (PythonServiceException e) {
            logger.error("Python service is unavailable at the moment. Please try again later.");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
