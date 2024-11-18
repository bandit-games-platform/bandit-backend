package be.kdg.int5.gameRegistry.adapters.out.db.game;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(schema = "game_registry", name = "developer")
public class DeveloperJpaEntity {
    @Id
    private UUID id;
    private String studioName;

    public DeveloperJpaEntity() {
    }

    public DeveloperJpaEntity(UUID id, String studioName) {
        this.id = id;
        this.studioName = studioName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }
}
