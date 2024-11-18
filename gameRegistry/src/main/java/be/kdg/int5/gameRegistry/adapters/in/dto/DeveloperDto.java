package be.kdg.int5.gameRegistry.adapters.in.dto;

public class DeveloperDto {
    private String studioName;

    public DeveloperDto(String studioName) {
        this.studioName = studioName;
    }

    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }
}
