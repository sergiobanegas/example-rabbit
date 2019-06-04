package com.worldmessages.eu.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${CONFIG_FOLDER}/eu-service.properties")
public class EUServiceConfig {

    @Value("${message.queue.name}")
    private String messageQueueName;

    @Bean
    public Queue queue() {
        return new Queue(messageQueueName);
    }

}
