package be.kdg.int5.gameRegistry.adapters.out.db.apiKey;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "dev_api_key")
public class DeveloperApiKeyJpaEntity {
    @Id
    private String apiKey;
    private UUID developerId;
    private boolean revoked;

    public DeveloperApiKeyJpaEntity() {

    }

    public DeveloperApiKeyJpaEntity(UUID developerId, String apiKey, boolean revoked) {
        this.developerId = developerId;
        this.apiKey = apiKey;
        this.revoked = revoked;
    }

    public String getApiKey() {
        return apiKey;
    }

    public UUID getDeveloperId() {
        return developerId;
    }

    public boolean isRevoked() {
        return revoked;
    }
}
