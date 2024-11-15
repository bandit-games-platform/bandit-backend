package be.kdg.int5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="be.kdg.int5.*.*Application"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern="be.kdg.int5.*.config.*SecurityConfig")
})
public class CompleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompleteApplication.class, args);
    }
}
