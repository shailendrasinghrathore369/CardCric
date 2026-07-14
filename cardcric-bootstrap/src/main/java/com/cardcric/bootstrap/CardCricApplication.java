package com.cardcric.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.cardcric")
public class CardCricApplication {

    public static void main(String[] args) {
        SpringApplication.run(CardCricApplication.class, args);
    }
}
