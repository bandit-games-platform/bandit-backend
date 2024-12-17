package be.kdg.int5.player.config;

import be.kdg.int5.player.adapters.in.dto.Action;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToActionConverter implements Converter<String, Action> {
    @Override
    public Action convert(String source) {
        try {
            return Action.valueOf(source.toUpperCase()); // Converts the string to the enum value
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid action value: " + source, e);
        }
    }
}

