package com.worldmessages.messageengine.service;

import com.worldmessages.messageengine.entity.Message;
import com.worldmessages.messageengine.payload.GetMessagesResponse;
import com.worldmessages.messageengine.payload.NewMessageRequest;
import com.worldmessages.messageengine.payload.NewMessageResponse;
import com.worldmessages.messageengine.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageEngineService {

    private static final String MESSAGE_SAVED_RESPONSE = "The message was saved";

    private final MessageRepository messageRepository;

    @Autowired
    public MessageEngineService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public NewMessageResponse saveMessage(final NewMessageRequest request) {
        messageRepository.save(new Message(request.getBody(), request.getReceiver()));
        return new NewMessageResponse(MESSAGE_SAVED_RESPONSE);
    }

    public GetMessagesResponse getMessages() {
        final List<Message> messages = messageRepository.findAll();
        return new GetMessagesResponse(messages);
    }

}
