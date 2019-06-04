package com.worldmessages.spain.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${CONFIG_FOLDER}/spain-service.properties")
public class SpainServiceConfig {

    @Value("${message.queue.name}")
    private String messageQueueName;

    @Bean
    public Queue queue() {
        return new Queue(messageQueueName);
    }

}
