package com.example.luna_forum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LunaForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunaForumApplication.class, args);
    }
}


