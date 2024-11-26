package be.kdg.int5.statistics.adapters.in.dto;

public class NewAchievementProgressDto {
    private Integer newProgressAmount;

    public NewAchievementProgressDto() {
    }

    public NewAchievementProgressDto(Integer newProgressAmount) {
        this.newProgressAmount = newProgressAmount;
    }

    public Integer getNewProgressAmount() {
        return newProgressAmount;
    }

    public void setNewProgressAmount(Integer newProgressAmount) {
        this.newProgressAmount = newProgressAmount;
    }
}
