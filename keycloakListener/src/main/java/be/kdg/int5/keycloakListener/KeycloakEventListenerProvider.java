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
            } catch (IOException | URISyntaxException e) {
                LOGGER.error("Something went wrong sending the url {}", e.getMessage());
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

    private boolean attemptConnection(String urlString, String userId, String username) throws IOException, URISyntaxException {
        HttpURLConnection connection = null;
        try {
            URL url = new URI(urlString).toURL();
            LOGGER.info("Attempting to connect to the URL: {}", url);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String jsonInputString = "{\"userId\": \"%s\", \"username\": \"%s\"}".formatted(userId, username);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return true;  // Connection succeeded
            }
        } catch (IOException | URISyntaxException e) {
            // Log or handle the error for the current URL
            LOGGER.error("Failed to connect to {}: {}", urlString, e.getMessage());
            return false;
        }

        connection.disconnect();
        return false;
    }
}
