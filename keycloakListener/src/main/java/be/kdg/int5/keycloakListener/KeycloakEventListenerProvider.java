package be.kdg.int5.keycloakListener;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.UUID;

public class KeycloakEventListenerProvider implements EventListenerProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeycloakEventListenerProvider.class);
    private static final String authorizationId = "4b0f0827-3951-4839-85de-7e4335069389";

    @Override
    public void onEvent(Event event) {
        LOGGER.info("Event with type: {} occurred", event.getType().toString());

        if(Objects.equals(event.getType().toString(), "REGISTER")) {
            // Get userId, then in details username
            // event.getDetails().entrySet() has these values:
            //      auth_method=openid-connect, auth_type=code, register_method=form, last_name=Gordon,
            //      redirect_uri=http://localhost:8180/authentication-complete, first_name=Roman,
            //      code_id=f192b9a6-8009-4ede-ac72-971f5fab17e6, email=roman.gordon@student.kdg.be,
            //      username=roman.gordon@student.kdg.be

            String userId = event.getUserId();
            String username = "";

            if (event.getDetails() != null) {
                for (Entry<String, String> e : event.getDetails().entrySet()) {
                    if (Objects.equals(e.getKey(), "username")) {
                        username = e.getValue();
                    }
                }
            }

            LOGGER.info("Registration for user with id {} and username {}", userId, username);

            UUID identifyingId = UUID.nameUUIDFromBytes((authorizationId + "-" + userId + "-" + username).getBytes());

            boolean connected = false;
            String individualContextUrl = "http://localhost:8094/player/registration/" + identifyingId.toString();
            String wholeApplicationUrl = "http://localhost:8090/api/registration/" + identifyingId.toString();

            try {
                connected = attemptConnection(individualContextUrl, userId, username);
                if (!connected) {
                    connected = attemptConnection(wholeApplicationUrl, userId, username);
                }
            } catch (IOException | InterruptedException e) {
                LOGGER.error("Something went wrong sending the url: {}", e.getMessage());
            }
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        System.out.println("Admin Event Occurred");
    }

    @Override
    public void close() {
    }

    private boolean attemptConnection(String urlString, String userId, String username) throws IOException, InterruptedException {
        String jsonInputString = """
                    {
                        "userId": "%s",
                        "username": "%s"
                    }
                    """.formatted(userId, username);

        try {
            LOGGER.info("Attempting to send to URL: {}", urlString);

            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                    .uri(URI.create(urlString))
                    .header("Content-Type", "application/json")
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.info("Got status {}", response.statusCode());

            client.close();

            if (response.statusCode() == 200) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Failed to connect to {}: {}. Exception type: {}", urlString, e.getMessage(), e.getClass().getCanonicalName());
            return false;
        }

        return false;
    }
}
