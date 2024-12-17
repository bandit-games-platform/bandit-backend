package be.kdg.int5.statistics;

import be.kdg.int5.statistics.adapters.out.db.playerGameStats.PlayerGameStatsJpaRepository;
import be.kdg.int5.statistics.port.in.ExportStatisticsCSVForGameCommand;
import be.kdg.int5.statistics.port.in.ExportStatisticsCSVForGameUseCase;
import be.kdg.int5.statistics.utils.CSVGeneratorUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ExportStatisticsCSVControllerIntegrationTest extends AbstractDatabaseTest {

    @MockBean
    private ExportStatisticsCSVForGameUseCase exportStatisticsCSVForGameUseCase;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CSVGeneratorUtil csvGeneratorUtil;

    private UUID gameId;
    private UUID adminId;
    private UUID notAnAdminId;
    private String csvContent;

    @BeforeEach
    void setUp() {
        adminId = Variables.ADMIN_ID;
        gameId = Variables.GAME_ID;
        notAnAdminId = Variables.NOT_AN_ADMIN_ID;
        csvContent = Variables.CSV_CONTENT;
    }

    @Test
    void whenAdminGeneratesCsvForSessionsShouldSucceed() throws Exception {

        // Arrange
        ExportStatisticsCSVForGameCommand command = new ExportStatisticsCSVForGameCommand(gameId);

        when(exportStatisticsCSVForGameUseCase.generateCSVForCompletedSessionsForGame(
                command))
                .thenReturn(csvContent);

        // Act
        mockMvc.perform(get("/export/statistics/games/" + gameId + "/sessions")
                .with(jwt()
                        .jwt(jwt -> jwt.claim("sub", String.valueOf(adminId)))
                        .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=completed_sessions_game_" + gameId + ".csv"))
                .andExpect(content().contentType("text/csv; charset=UTF-8"))
                .andExpect(content().string(csvContent));

        verify(exportStatisticsCSVForGameUseCase).generateCSVForCompletedSessionsForGame(
                new ExportStatisticsCSVForGameCommand(gameId));
    }


    @Test
    void whenNotAnAdminGeneratesCsvForSessionsShouldReturnForbidden() throws Exception {

        // Arrange
        ExportStatisticsCSVForGameCommand command = new ExportStatisticsCSVForGameCommand(gameId);

        when(exportStatisticsCSVForGameUseCase.generateCSVForCompletedSessionsForGame(
                command))
                .thenReturn(csvContent);

        // Act
        mockMvc.perform(get("/export/statistics/games/" + gameId + "/sessions")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(notAnAdminId)))
                                .authorities(new SimpleGrantedAuthority("player"))))
                .andExpect(status().isForbidden());

        verify(exportStatisticsCSVForGameUseCase,times(0)).generateCSVForCompletedSessionsForGame(
                new ExportStatisticsCSVForGameCommand(gameId));
    }

    @Test
    void whenAdminGeneratesCsvForAchievementsShouldSucceed() throws Exception {

        // Arrange
        ExportStatisticsCSVForGameCommand command = new ExportStatisticsCSVForGameCommand(gameId);

        when(exportStatisticsCSVForGameUseCase.generateCSVForAchievementProgressForGame(
                command))
                .thenReturn(csvContent);

        // Act
        mockMvc.perform(get("/export/statistics/games/" + gameId + "/achievements")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(adminId)))
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=achievement_progress_game_" + gameId + ".csv"))
                .andExpect(content().contentType("text/csv; charset=UTF-8"))
                .andExpect(content().string(csvContent));

        verify(exportStatisticsCSVForGameUseCase).generateCSVForAchievementProgressForGame(
                new ExportStatisticsCSVForGameCommand(gameId));
    }


    @Test
    void whenNotAnAdminGeneratesCsvForAchievementsShouldReturnForbidden() throws Exception {

        // Arrange
        ExportStatisticsCSVForGameCommand command = new ExportStatisticsCSVForGameCommand(gameId);

        when(exportStatisticsCSVForGameUseCase.generateCSVForAchievementProgressForGame(
                command))
                .thenReturn(csvContent);

        // Act
        mockMvc.perform(get("/export/statistics/games/" + gameId + "/achievements")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(notAnAdminId)))
                                .authorities(new SimpleGrantedAuthority("player"))))
                .andExpect(status().isForbidden());

        verify(exportStatisticsCSVForGameUseCase,times(0)).generateCSVForAchievementProgressForGame(
                new ExportStatisticsCSVForGameCommand(gameId));
    }

    @Test
    void whenAdminGeneratesCsvForEmptySessionsShouldReturnNoContent() throws Exception {

        // Arrange
        ExportStatisticsCSVForGameCommand command = new ExportStatisticsCSVForGameCommand(gameId);

        when(exportStatisticsCSVForGameUseCase.generateCSVForCompletedSessionsForGame(
                command))
                .thenReturn(null);

        // Act
        mockMvc.perform(get("/export/statistics/games/" + gameId + "/sessions")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(adminId)))
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(status().isNoContent());

        verifyNoInteractions(csvGeneratorUtil);
    }

    @Test
    void whenAdminGeneratesCsvForEmptyAchievementsShouldReturnNoContent() throws Exception {

        // Arrange
        ExportStatisticsCSVForGameCommand command = new ExportStatisticsCSVForGameCommand(gameId);

        when(exportStatisticsCSVForGameUseCase.generateCSVForAchievementProgressForGame(
                command))
                .thenReturn(null);

        // Act
        mockMvc.perform(get("/export/statistics/games/" + gameId + "/achievements")
                        .with(jwt()
                                .jwt(jwt -> jwt.claim("sub", String.valueOf(adminId)))
                                .authorities(new SimpleGrantedAuthority("admin"))))
                .andExpect(status().isNoContent());

        verifyNoInteractions(csvGeneratorUtil);
    }
}