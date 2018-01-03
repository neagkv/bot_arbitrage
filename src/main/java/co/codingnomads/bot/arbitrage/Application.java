package co.codingnomads.bot.arbitrage;

/**
 * @author Kevin Neag
 */

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

    @SpringBootApplication
    public class Application {

        @Autowired
        Controller controller;

        public static void main(String args[]) {
            SpringApplication.run(Application.class);
        }

        @Bean
        public CommandLineRunner run() throws Exception {
            return args -> {
                controller.runBot();
            };
        }

    }

