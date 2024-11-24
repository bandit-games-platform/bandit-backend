package be.kdg.int5.statistics.port.in.query;

public interface PlayerTotalPlayTimeQuery {
    long getTotalPlayTimeSecondsForPlayer(PlayerTotalPlayTimeCommand command);
}
