package be.kdg.int5.statistics.port.out;

import be.kdg.int5.statistics.adapters.out.python.dto.WinProbabilityModelFeaturesDto;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;

import java.util.List;

public interface PredictWinProbabilityPort {
    WinPrediction getPredictions(List<WinProbabilityModelFeaturesDto> featuresDto);
}
