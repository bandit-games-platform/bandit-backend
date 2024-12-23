package be.kdg.int5.statistics.port.out;

import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityFeaturesDto;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;

public interface PredictWinProbabilityPort {
    WinPrediction getPredictions(WinProbabilityFeaturesDto featuresDto);
}
