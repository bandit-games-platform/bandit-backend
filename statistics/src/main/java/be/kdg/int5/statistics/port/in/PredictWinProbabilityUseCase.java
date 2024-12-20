package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.domain.PlayerGameStats;
import be.kdg.int5.statistics.domain.PlayerId;

import java.util.List;

public interface PredictWinProbabilityUseCase {
    List<PlayerGameStats> predictWinProbability(PredictWinProbabilityCommand command);
}
