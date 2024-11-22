package be.kdg.int5.statistics.adapters.out.other;

import be.kdg.int5.statistics.adapters.out.other.dto.AchievementIdDto;
import be.kdg.int5.statistics.adapters.out.other.dto.DeveloperIdDto;
import be.kdg.int5.statistics.domain.AchievementId;
import be.kdg.int5.statistics.domain.GameId;
import be.kdg.int5.statistics.port.out.GameRegistryCallPort;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;

@Component
public class GameRegistryCallAdapter implements GameRegistryCallPort {
    private static final Logger logger = LoggerFactory.getLogger(GameRegistryCallAdapter.class);
    @Value("${contexts.gameRegistry.baseUrl}")
    private String gameRegistryBaseUrl;

    @Override
    public boolean doesDeveloperOwnGame(UUID developerId, GameId gameId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(gameRegistryBaseUrl + "/games/" + gameId.uuid().toString() + "/developer"))
                    .header("Accept", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            client.close();

            DeveloperIdDto developerIdDto;
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                developerIdDto = objectMapper.readValue(response.body(), DeveloperIdDto.class);
            } else {
                return false;
            }

            boolean ownsGame = developerIdDto.getUuid().equals(developerId);
            logger.info("statistics: Developer {} {} the game {}", developerId, ownsGame ? "owns" : "does not own", gameId.uuid());

            return ownsGame;
        } catch (InterruptedException | IOException e) {
            logger.error("statistics: Could not get who owns the game from game registry");
            return false;
        }
    }

    @Override
    public boolean doesAchievementBelongToGame(AchievementId achievementId, GameId gameId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(gameRegistryBaseUrl + "/games/" + gameId.uuid().toString() + "/achievements"))
                    .header("Accept", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            client.close();

            List<AchievementIdDto> achievementIdDtos;
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                achievementIdDtos = objectMapper.readValue(response.body(), new TypeReference<List<AchievementIdDto>>() {});
            } else {
                return false;
            }

            boolean belongsToGame = achievementIdDtos.stream()
                    .anyMatch(achievementIdDto -> achievementIdDto.getUuid().equals(achievementId.uuid()));
            logger.info("statistics: Achievement {} {} game {}", achievementId.uuid(), belongsToGame ? "belongs to" : "does not belong to", gameId.uuid());

            return belongsToGame;
        } catch (InterruptedException | IOException e) {
            logger.error("statistics: Could not get achievement status from game registry");
            return false;
        }
    }
}
