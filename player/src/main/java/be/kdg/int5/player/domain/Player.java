package be.kdg.int5.player.domain;

import be.kdg.int5.common.domain.ImageResource;
import java.time.LocalDate;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class Player {
    private final PlayerId id;
    private String displayName;
    private Gender gender;
    private Country location;
    private LocalDate birthdate;
    private ImageResource avatar;
    private List<Player> friendsList;

    public Player(final PlayerId id, final String displayName) {
        requireNonNull(displayName);

        this.id = id;
        this.displayName = displayName;
    }
}
