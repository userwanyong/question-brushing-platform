package com.questionbrushingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuestionBrushingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionBrushingPlatformApplication.class, args);
    }

}
