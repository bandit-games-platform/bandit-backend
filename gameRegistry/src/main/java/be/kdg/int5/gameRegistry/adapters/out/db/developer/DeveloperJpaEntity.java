package be.kdg.int5.gameRegistry.adapters.out.db.developer;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeveloperJpaEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStudioName(), that.getStudioName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStudioName());
    }
}
