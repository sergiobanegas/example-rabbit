package com.worldmessages.japan.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${CONFIG_FOLDER}/japan-service.properties")
public class JapanServiceConfig {

    @Value("${message.queue.name}")
    private String messageQueueName;

    @Bean
    public Queue queue() {
        return new Queue(messageQueueName);
    }

}
