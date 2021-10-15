package com.tekcapsule.topic.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.topic","com.tekcapsule.core"})
public class TopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicApplication.class, args);
    }
}
