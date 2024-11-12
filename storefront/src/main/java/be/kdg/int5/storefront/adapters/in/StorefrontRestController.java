package be.kdg.int5.storefront.adapters.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorefrontRestController {


    @GetMapping("/hello/storefront")
    public void sayHelloB(){
        System.out.println("Hello Storefront");
    }

}
