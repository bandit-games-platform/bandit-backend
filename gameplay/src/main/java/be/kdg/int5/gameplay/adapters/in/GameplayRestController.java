package be.kdg.int5.gameplay.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameplayRestController {


    @GetMapping("/hello/gameplay")
    public void sayHelloB(){
        System.out.println("Hello Gameplay Context");
    }

}
