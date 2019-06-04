package com.worldmessages.messagemanager.service.impl;

import com.worldmessages.messagemanager.client.MessageEngineClient;
import com.worldmessages.messagemanager.client.dto.GetMessagesOutDTO;
import com.worldmessages.messagemanager.client.dto.NewMessageInDTO;
import com.worldmessages.messagemanager.client.dto.NewMessageOutDTO;
import com.worldmessages.messagemanager.domain.ReceiverZone;
import com.worldmessages.messagemanager.domain.Union;
import com.worldmessages.messagemanager.payload.GetMessagesResponse;
import com.worldmessages.messagemanager.payload.SendMessageRequest;
import com.worldmessages.messagemanager.payload.SendMessageResponse;
import com.worldmessages.messagemanager.service.MessageService;
import com.worldmessages.messagemanager.util.QueueUtil;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final RabbitTemplate template;

    private final DirectExchange directExchange;

    private final FanoutExchange fanoutExchange;

    private final TopicExchange topicExchange;

    private final MessageEngineClient messageEngineClient;

    @Autowired
    public MessageServiceImpl(RabbitTemplate template, DirectExchange directExchange, FanoutExchange fanoutExchange, TopicExchange topicExchange, MessageEngineClient messageEngineClient) {
        this.template = template;
        this.directExchange = directExchange;
        this.fanoutExchange = fanoutExchange;
        this.topicExchange = topicExchange;
        this.messageEngineClient = messageEngineClient;
    }

    @Override
    public SendMessageResponse sendMessage(final SendMessageRequest request) {
        final ReceiverZone receiver = request.getReceiver();
        handleMessage(request, receiver);
        final NewMessageOutDTO newMessageOutDTO = callMessageEngineToSaveMessage(request);
        return new SendMessageResponse(newMessageOutDTO.getResponse());
    }

    @Override
    public GetMessagesResponse getMessages() {
        final GetMessagesOutDTO messages = messageEngineClient.getMessages();
        return new GetMessagesResponse(messages.getContent());
    }

    private void handleMessage(final SendMessageRequest request, final ReceiverZone receiver) {
        final String body = request.getBody();
        if (receiver.hasValue(ReceiverZone.WORLD)) {
            sendMessageToAll(body);
        } else if (Union.countryBelongsToAnUnion(receiver)) {
            sendMessageToSpecificReceivers(body, QueueUtil.getQueueName(receiver));
        } else {
            sendMessageToSpecificReceiver(body, QueueUtil.getQueueName(receiver));
        }
    }

    private NewMessageOutDTO callMessageEngineToSaveMessage(final SendMessageRequest request) {
        return messageEngineClient.createMessage(new NewMessageInDTO(request.getBody(), request.getReceiver()));
    }

    private void sendMessageToAll(final String message) {
        sendMessageToExchange(fanoutExchange.getName(), message, "");
    }

    private void sendMessageToSpecificReceiver(final String message, final String routingKey) {
        sendMessageToExchange(directExchange.getName(), message, routingKey);
    }

    private void sendMessageToSpecificReceivers(final String message, final String routingKey) {
        sendMessageToExchange(topicExchange.getName(), message, routingKey);
    }

    private void sendMessageToExchange(final String exchangeName, final String message, final String routingKey) {
        template.convertAndSend(exchangeName, routingKey, message);
    }

}
