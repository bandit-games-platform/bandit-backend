package be.kdg.int5.chatbot.adapters.in;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotRestController {


    @GetMapping("/hello/chatbot")
    public void sayHelloA(){
        System.out.println("Hello Chatbot");
    }

}
