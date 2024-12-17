package be.kdg.int5.statistics.port.in;

public interface ExportStatisticsCSVForGameUseCase {
    String generateCSVForCompletedSessionsForGame(ExportStatisticsCSVForGameCommand command);

    String generateCSVForAchievementProgressForGame(ExportStatisticsCSVForGameCommand command);
}
