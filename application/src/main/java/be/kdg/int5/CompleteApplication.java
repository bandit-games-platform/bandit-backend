package be.kdg.int5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="be.kdg.int5.*.*Application"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="be.kdg.int5.*.config.*SecurityConfig"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="be.kdg.int5.*.config.*ApplicationConfig")
})
public class CompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompleteApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
