package co.codingnomads.bot.arbitrage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Kevin Neag
 */

/**
 * Application Class for starting the entire application
 */
@SpringBootApplication
    public class Application {

        @Autowired
        Controller controller;

        public static void main(String args[]) {
            SpringApplication.run(Application.class);
        }

    /**
     * CommandLineRunner method that starts the controller runBot method from the command line
     * @throws Exception
     */
        @Bean
        public CommandLineRunner run() throws Exception {
            return args -> {
                controller.runBot();
            };
        }

    }

