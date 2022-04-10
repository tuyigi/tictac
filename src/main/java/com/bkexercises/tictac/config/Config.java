package com.bkexercises.tictac.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${player.server}")
    char server;
    @Value("${player.opponent}")
    char opponent;

    public char getServer() {
        return server;
    }

    public char getOpponent() {
        return opponent;
    }

}
