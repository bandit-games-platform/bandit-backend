package be.kdg.int5.statistics.core;

import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.port.in.ExportStatisticsCSVForGameCommand;
import be.kdg.int5.statistics.port.in.ExportStatisticsCSVForGameUseCase;
import be.kdg.int5.statistics.port.out.PlayerGameStatisticsLoadPort;
import be.kdg.int5.statistics.utils.CSVGeneratorUtil;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExportStatisticsCSVForGameUseCaseImpl implements ExportStatisticsCSVForGameUseCase {
    private final PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort;
    private final CSVGeneratorUtil csvGeneratorUtil;

    public ExportStatisticsCSVForGameUseCaseImpl(PlayerGameStatisticsLoadPort playerGameStatisticsLoadPort, CSVGeneratorUtil csvGeneratorUtil) {
        this.playerGameStatisticsLoadPort = playerGameStatisticsLoadPort;
        this.csvGeneratorUtil = csvGeneratorUtil;
    }

    @Override
    public String generateCSVForCompletedSessionsForGame(ExportStatisticsCSVForGameCommand command) {
        List<PlayerGameStats> playerGameStatsList = playerGameStatisticsLoadPort.loadAllPlayerGameStatsForGame(
                new GameId(command.gameId()));

        if (playerGameStatsList.isEmpty()) {
            return null;
        }
        return csvGeneratorUtil.convertSessionsToCsv(playerGameStatsList);
    }

    @Override
    public String generateCSVForAchievementProgressForGame(ExportStatisticsCSVForGameCommand command) {
        List<PlayerGameStats> playerGameStatsList = playerGameStatisticsLoadPort.loadAllPlayerGameStatsForGame(
                new GameId(command.gameId()));

        if (playerGameStatsList.isEmpty()) {
            return null;
        }
        return csvGeneratorUtil.convertAchievementProgressListToCsv(playerGameStatsList);
    }
}
