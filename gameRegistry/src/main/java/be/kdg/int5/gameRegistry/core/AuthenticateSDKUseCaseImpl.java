package be.kdg.int5.gameRegistry.core;

import be.kdg.int5.gameRegistry.domain.DeveloperApiKey;
import be.kdg.int5.gameRegistry.port.in.AuthenticateSDKCommand;
import be.kdg.int5.gameRegistry.port.in.AuthenticateSDKUseCase;
import be.kdg.int5.gameRegistry.port.out.LoadDeveloperApiKeyPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class AuthenticateSDKUseCaseImpl implements AuthenticateSDKUseCase {
    private final Logger logger = LoggerFactory.getLogger(AuthenticateSDKUseCaseImpl.class);

    @Value("${keycloak.host}")
    private String keycloakHost;
    @Value("${keycloak.realm}")
    private String keycloakRealm;
    @Value("${keycloak.client.id}")
    private String keycloakClientId;
    @Value("${keycloak.client.secret}")
    private String keycloakClientSecret;

    private final LoadDeveloperApiKeyPort loadDeveloperApiKeyPort;

    public AuthenticateSDKUseCaseImpl(LoadDeveloperApiKeyPort loadDeveloperApiKeyPort) {
        this.loadDeveloperApiKeyPort = loadDeveloperApiKeyPort;
    }

    @Override
    public String authenticate(AuthenticateSDKCommand command) throws InvalidApiKeyException {
        Optional<DeveloperApiKey> apiKeyOptional = loadDeveloperApiKeyPort.load(command.apiKey());
        if (apiKeyOptional.isEmpty()) {
            logger.warn("gameRegistry:sdk-auth Auth attempt with non-existent api key: '{}'", command.apiKey());
            throw new InvalidApiKeyException();
        }

        DeveloperApiKey developerApiKey = apiKeyOptional.get();
        String developerId = developerApiKey.getDeveloperId().uuid().toString();

        if (developerApiKey.isRevoked()) {
            logger.warn("gameRegistry:sdk-auth Auth attempt with revoked api key: '{}' of developer '{}'",
                    developerApiKey.getApiKey(),
                    developerId
            );
            throw new InvalidApiKeyException();
        }
        logger.info("gameRegistry:sdk-auth [apiKey accepted] Impersonating developer '{}'...", developerId);

        try (
                HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build()
        ) {
            // Request access token using token exchange for the developer corresponding to the given apiKey
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(keycloakHost+"/realms/"+keycloakRealm+"/protocol/openid-connect/token"))
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "client_id="+keycloakClientId
                            +"&client_secret="+keycloakClientSecret
                            +"&grant_type=urn:ietf:params:oauth:grant-type:token-exchange"
                            +"&requested_subject="+developerId
                            +"&requested_token_type=urn:ietf:params:oauth:token-type:access_token"
                    )).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                logger.warn("gameRegistry:sdk-auth Received non-OK keycloak response status: {} \nbody: {}\nheaders: {}",
                        response.statusCode(),
                        response.body(),
                        response.headers()
                );
                throw new ImpersonationFailedException();
            }

            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new ImpersonationFailedException();
        }
    }
}
