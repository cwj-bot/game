package com.weijian.game.poker;

import com.weijian.game.poker.spot21.server.Spot21Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
@ComponentScan("com.weijian")
public class PokerApplication {

    public static ApplicationContext context;
    public static Spot21Server spot21Server;
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
//        SpringApplication application = (SpringApplication) SpringApplication.run(PokerApplication.class, args);
//        context = application.run(args);

        SpringApplication application = new SpringApplication(PokerApplication.class);

        application.addListeners(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                logger.error("spring boot shutdown");
                logger.error("shutdown netty socket channel");
                spot21Server.stop();
            }
        });

        context = application.run(args);
        spot21Server = new Spot21Server();
        spot21Server.start();
    }

}
