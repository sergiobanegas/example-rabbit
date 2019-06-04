package com.worldmessages.messagemanager.client;

import com.worldmessages.messagemanager.client.dto.GetMessagesOutDTO;
import com.worldmessages.messagemanager.client.dto.NewMessageInDTO;
import com.worldmessages.messagemanager.client.dto.NewMessageOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-engine", url = "${message-engine.uri}")
public interface MessageEngineClient {

    @RequestMapping(method = RequestMethod.POST, value = "/messages", consumes = "application/json")
    NewMessageOutDTO createMessage(NewMessageInDTO request);

    @RequestMapping(method = RequestMethod.GET, value = "/messages", consumes = "application/json")
    GetMessagesOutDTO getMessages();

}
