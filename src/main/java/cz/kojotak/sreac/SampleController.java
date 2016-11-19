package cz.kojotak.sreac;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@EnableAutoConfiguration
public class SampleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
    	System.out.println("home...");
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
    	System.out.println("main...");
        SpringApplication.run(SampleController.class, args);
    }
}
