package com.worldmessages.messagemanager.controller;

import com.worldmessages.messagemanager.payload.GetMessagesResponse;
import com.worldmessages.messagemanager.payload.SendMessageRequest;
import com.worldmessages.messagemanager.payload.SendMessageResponse;
import com.worldmessages.messagemanager.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/messages")
@Api(value = "Messages", description = "REST API for Messages", tags = {"Messages"})
public class MessageManagerController {

    private final MessageService messageService;

    @Autowired
    public MessageManagerController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @ApiOperation(value = "Send a message", tags = {"Messages"})
    public SendMessageResponse sendMessage(@RequestBody @Valid final SendMessageRequest message) {
        return messageService.sendMessage(message);
    }

    @GetMapping
    @ApiOperation(value = "Get messages", tags = {"Messages"})
    public GetMessagesResponse getMessages() {
        return messageService.getMessages();
    }

}
