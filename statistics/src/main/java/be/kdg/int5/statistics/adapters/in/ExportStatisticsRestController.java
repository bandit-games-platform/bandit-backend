package be.kdg.int5.statistics.adapters.in;

import be.kdg.int5.statistics.port.in.ExportStatisticsCSVForGameCommand;
import be.kdg.int5.statistics.port.in.ExportStatisticsCSVForGameUseCase;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
@RequestMapping("/export")
public class ExportStatisticsRestController {
    private final ExportStatisticsCSVForGameUseCase exportStatisticsCSVForGameUseCase;

    public ExportStatisticsRestController(ExportStatisticsCSVForGameUseCase exportStatisticsCSVForGameUseCase) {
        this.exportStatisticsCSVForGameUseCase = exportStatisticsCSVForGameUseCase;
    }

    @GetMapping("/statistics/games/{gameId}/completed-sessions")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<byte[]> exportCompletedSessionsForGame(
            @NotNull @PathVariable UUID gameId) {
        String csvContent = exportStatisticsCSVForGameUseCase.generateCSVForCompletedSessionsForGame(
                new ExportStatisticsCSVForGameCommand(gameId)
        );

        if (csvContent == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=completed_sessions_game_" + gameId + ".csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        return new ResponseEntity<>(csvContent.getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
    }

    @GetMapping("/statistics/games/{gameId}/achievement-progress")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<byte[]> exportAchievementProgressForGame(
            @NotNull @PathVariable UUID gameId) {
        String csvContent = exportStatisticsCSVForGameUseCase.generateCSVForAchievementProgressForGame(
                new ExportStatisticsCSVForGameCommand(gameId)
        );

        if (csvContent == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=achievement_progress_game_" + gameId + ".csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv; charset=UTF-8");

        return new ResponseEntity<>(csvContent.getBytes(StandardCharsets.UTF_8), headers, HttpStatus.OK);
    }
}
