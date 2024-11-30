package be.kdg.int5.storefront.adapters.out.other;

import be.kdg.int5.storefront.adapters.out.other.dto.GameDetailsDto;
import be.kdg.int5.storefront.port.out.GameRegistryContextCallPort;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// This can later be replaced by a projection when confirmation is received from programming teachers about which
// is the best way to get/store the price
@Component
public class GameRegistryContextCallAdapter implements GameRegistryContextCallPort {
    private static final Logger logger = LoggerFactory.getLogger(GameRegistryContextCallAdapter.class);
    @Value("${contexts.gameRegistry.baseUrl}")
    private String gameRegistryBaseUrl;

    @Override
    public Map<String, String> getGameTitleAndPriceFromId(UUID gameId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(gameRegistryBaseUrl + "/games/" + gameId.toString()))
                    .header("Accept", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            client.close();

            GameDetailsDto gameDetailsDto;
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                gameDetailsDto = objectMapper.readValue(response.body(), GameDetailsDto.class);
            } else {
                return null;
            }

            logger.info("storefront: Game {} has title {} and price {}",
                    gameDetailsDto.getId().toString(),
                    gameDetailsDto.getTitle(),
                    gameDetailsDto.getPrice().toString()
            );

            Map<String, String> basicDetailsMap = new HashMap<>();

            basicDetailsMap.put("gameId", gameDetailsDto.getId().toString());
            basicDetailsMap.put("title", gameDetailsDto.getTitle());
            basicDetailsMap.put("price", gameDetailsDto.getPrice().toString());

            return basicDetailsMap;
        } catch (InterruptedException | IOException e) {
            logger.error("storefront: Could not get basic game details from registry");
            return null;
        }
    }
}
