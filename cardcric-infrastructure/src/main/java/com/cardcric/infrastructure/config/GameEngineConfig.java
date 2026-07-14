package com.cardcric.infrastructure.config;

import com.cardcric.domain.service.CricketGameEngine;
import com.cardcric.domain.service.GameEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class GameEngineConfig {

    @Bean
    public GameEngine gameEngine() {
        return new CricketGameEngine(new Random());
    }
}
