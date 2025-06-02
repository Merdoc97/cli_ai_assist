package ua.ai_assist.ai_assist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AiAssistApplication {

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(
                new SpringApplicationBuilder(AiAssistApplication.class)
                        .web(WebApplicationType.NONE)
                        .build().run(args)));
    }

}
