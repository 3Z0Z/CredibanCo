package com.credibanco.assessment.card;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class CredibanCoApplication {
    
    @CrossOrigin(origins = "*")
    public static void main(String[] args) {
        SpringApplication.run(CredibanCoApplication.class, args);
    }
}
