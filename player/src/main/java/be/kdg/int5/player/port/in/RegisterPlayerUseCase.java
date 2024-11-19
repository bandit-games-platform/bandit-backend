package be.kdg.int5.player.port.in;

public interface RegisterPlayerUseCase {
    void registerOrUpdatePlayerAccount(RegisterPlayerCommand command);
}
