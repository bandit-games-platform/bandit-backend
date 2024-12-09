package be.kdg.int5.player.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final StringToActionConverter stringToActionConverter;

    public WebConfig(StringToActionConverter stringToActionConverter) {
        this.stringToActionConverter = stringToActionConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToActionConverter);
    }
}

