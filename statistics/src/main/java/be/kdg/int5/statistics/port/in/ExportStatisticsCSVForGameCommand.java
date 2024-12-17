package be.kdg.int5.statistics.port.in;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record ExportStatisticsCSVForGameCommand(UUID gameId) {
    public ExportStatisticsCSVForGameCommand {
        requireNonNull(gameId);
    }
}
