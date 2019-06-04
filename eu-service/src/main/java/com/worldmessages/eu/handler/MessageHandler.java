package com.worldmessages.eu.handler;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@RabbitListener(queues = "${message.queue.name}")
@Component
public class MessageHandler {

    private static final Logger LOGGER = Logger.getLogger(MessageHandler.class.getName());
    private static final String RECEIVED_MESSAGE_FORMAT = "Received message: '%s'";

    @RabbitHandler
    public void receive(final String message) {
        LOGGER.info(String.format(RECEIVED_MESSAGE_FORMAT, message));
    }

}
