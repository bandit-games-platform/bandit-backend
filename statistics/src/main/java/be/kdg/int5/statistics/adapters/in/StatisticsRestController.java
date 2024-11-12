package be.kdg.int5.statistics.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsRestController {


    @GetMapping("/hello/statistics")
    public void sayHelloB(){
        System.out.println("Hello Statistics");
    }

}
