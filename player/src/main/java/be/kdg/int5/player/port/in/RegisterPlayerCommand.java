package be.kdg.int5.player.port.in;

import java.util.UUID;

public record RegisterPlayerCommand(UUID id, String username) {
}
