package be.kdg.int5.gameRegistry.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GamesOverviewRestController {

    @GetMapping("/overview")
    public void getGameOverview(){
        System.out.println("Hello Game Registry");
    }
}
