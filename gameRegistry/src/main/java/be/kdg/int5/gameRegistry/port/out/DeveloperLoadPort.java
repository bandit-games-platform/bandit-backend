package be.kdg.int5.gameRegistry.port.out;

import be.kdg.int5.gameRegistry.domain.Developer;
import be.kdg.int5.gameRegistry.domain.DeveloperId;

public interface DeveloperLoadPort {
    Developer load(DeveloperId developerId);
}
