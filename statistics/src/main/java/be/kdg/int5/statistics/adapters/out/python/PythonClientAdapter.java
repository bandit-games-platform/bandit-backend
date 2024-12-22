package be.kdg.int5.statistics.adapters.out.python;

import be.kdg.int5.common.exceptions.PythonServiceException;
import be.kdg.int5.statistics.adapters.out.python.dto.WinPredictionModeInputFeaturesDto;
import be.kdg.int5.statistics.port.out.PredictWinProbabilityPort;
import be.kdg.int5.statistics.utils.predictiveModel.AggregatedPlayerSessionsData;
import be.kdg.int5.statistics.utils.predictiveModel.WinPrediction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class PythonClientAdapter implements PredictWinProbabilityPort {
    @Value("${python.url:http://localhost:8000/predict/win-probability}")
    private String pythonUrl;

    private final static Logger logger = LoggerFactory.getLogger(PythonClientAdapter.class);

    private final RestTemplate restTemplate;

    public PythonClientAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WinPrediction getPredictions(WinPredictionModeInputFeaturesDto featuresDto) {
        ObjectMapper objectMapper = new ObjectMapper();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WinPredictionModeInputFeaturesDto> entity = new HttpEntity<>(featuresDto, headers);

        try {
            String response = restTemplate.postForObject(pythonUrl, entity, String.class);
            logger.debug("Response Out Adapter - Win Probability: {}", response);

            return objectMapper.readValue(response, WinPrediction.class);
        } catch (Exception e) {
            throw new PythonServiceException("An error occurred while calling the Python service.", e);
        }
    }
}
