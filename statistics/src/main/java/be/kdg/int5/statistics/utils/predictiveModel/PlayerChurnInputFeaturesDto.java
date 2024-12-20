package be.kdg.int5.statistics.utils.predictiveModel;

public class PlayerChurnInputFeaturesDto {
    private Gender gender;
    private Country country;
    private int age;
    private int session_count;
    private int game_variety;
    private float avg_session_duration;
    private float avg_score;
    private float avg_opponent_score;
    private float win_rate;
    private int days_since_last_session;

    public PlayerChurnInputFeaturesDto() {
    }

    public PlayerChurnInputFeaturesDto(Gender gender, Country country, int age, int session_count, int game_variety, float avg_session_duration, float avg_score, float avg_opponent_score, float win_rate, int days_since_last_session) {
        this.gender = gender;
        this.country = country;
        this.age = age;
        this.session_count = session_count;
        this.game_variety = game_variety;
        this.avg_session_duration = avg_session_duration;
        this.avg_score = avg_score;
        this.avg_opponent_score = avg_opponent_score;
        this.win_rate = win_rate;
        this.days_since_last_session = days_since_last_session;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSession_count() {
        return session_count;
    }

    public void setSession_count(int session_count) {
        this.session_count = session_count;
    }

    public int getGame_variety() {
        return game_variety;
    }

    public void setGame_variety(int game_variety) {
        this.game_variety = game_variety;
    }

    public float getAvg_session_duration() {
        return avg_session_duration;
    }

    public void setAvg_session_duration(float avg_session_duration) {
        this.avg_session_duration = avg_session_duration;
    }

    public float getAvg_score() {
        return avg_score;
    }

    public void setAvg_score(float avg_score) {
        this.avg_score = avg_score;
    }

    public float getAvg_opponent_score() {
        return avg_opponent_score;
    }

    public void setAvg_opponent_score(float avg_opponent_score) {
        this.avg_opponent_score = avg_opponent_score;
    }

    public float getWin_rate() {
        return win_rate;
    }

    public void setWin_rate(float win_rate) {
        this.win_rate = win_rate;
    }

    public int getDays_since_last_session() {
        return days_since_last_session;
    }

    public void setDays_since_last_session(int days_since_last_session) {
        this.days_since_last_session = days_since_last_session;
    }
}
