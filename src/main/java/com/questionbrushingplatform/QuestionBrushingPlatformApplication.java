package com.questionbrushingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 永
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement //开启注解方式的事务管理
public class QuestionBrushingPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestionBrushingPlatformApplication.class, args);
    }

}
