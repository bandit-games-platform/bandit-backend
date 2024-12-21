package be.kdg.int5.statistics.port.in;

import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;

public interface PredictWinProbabilityUseCase {
    WinPrediction predictWinProbability(PredictWinProbabilityCommand command);
}
