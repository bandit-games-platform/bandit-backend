package be.kdg.int5.gameRegistry.domain;

import java.util.Objects;

public class DeveloperApiKey {
    private final DeveloperId developerId;
    private String apiKey;
    private boolean revoked;

    public DeveloperApiKey(DeveloperId developerId, String apiKey, boolean revoked) {
        this.developerId = Objects.requireNonNull(developerId);
        this.apiKey = Objects.requireNonNull(apiKey);
        this.revoked = revoked;
    }

    public DeveloperId getDeveloperId() {
        return developerId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void revoke() {
        this.revoked = true;
    }
}
