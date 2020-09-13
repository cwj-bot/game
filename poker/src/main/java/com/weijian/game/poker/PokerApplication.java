package com.weijian.game.poker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PokerApplication {

    public static ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication application = (SpringApplication) SpringApplication.run(PokerApplication.class, args);
        context = application.run(args);
    }

}
