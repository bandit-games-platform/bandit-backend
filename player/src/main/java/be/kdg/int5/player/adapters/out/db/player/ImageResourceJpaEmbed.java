package be.kdg.int5.player.adapters.out.db.player;

public class ImageResourceJpaEmbed {
    private String url;

    public ImageResourceJpaEmbed() {
    }

    public ImageResourceJpaEmbed(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
