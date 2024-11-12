package be.kdg.int5.player.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerRestController {


    @GetMapping("/hello/player")
    public void sayHelloB(){
        System.out.println("Hello Player");
    }

}
