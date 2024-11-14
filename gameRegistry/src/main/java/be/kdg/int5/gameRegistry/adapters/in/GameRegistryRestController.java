package be.kdg.int5.gameRegistry.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameRegistryRestController {


    @GetMapping("/hello/game-library")
    public void sayHelloB(){
        System.out.println("Hello Game Registry");
    }

}
