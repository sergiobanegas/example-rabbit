package com.worldmessages.italy.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${CONFIG_FOLDER}/italy-service.properties")
public class ItalyServiceConfig {

    @Value("${message.queue.name}")
    private String messageQueueName;

    @Bean
    public Queue queue() {
        return new Queue(messageQueueName);
    }

}
