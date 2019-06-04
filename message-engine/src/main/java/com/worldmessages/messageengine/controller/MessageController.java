package com.worldmessages.messageengine.controller;

import com.worldmessages.messageengine.payload.GetMessagesResponse;
import com.worldmessages.messageengine.payload.NewMessageRequest;
import com.worldmessages.messageengine.payload.NewMessageResponse;
import com.worldmessages.messageengine.service.MessageEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageEngineService messageEngineService;

    @Autowired
    public MessageController(MessageEngineService messageEngineService) {
        this.messageEngineService = messageEngineService;
    }

    @PostMapping
    public NewMessageResponse postMessage(@RequestBody @Valid final NewMessageRequest newMessageRequest) {
        return messageEngineService.saveMessage(newMessageRequest);
    }

    @GetMapping
    public GetMessagesResponse getMessages() {
        return messageEngineService.getMessages();
    }

}
