package com.worldmessages.messagemanager.service;

import com.worldmessages.messagemanager.payload.GetMessagesResponse;
import com.worldmessages.messagemanager.payload.SendMessageRequest;
import com.worldmessages.messagemanager.payload.SendMessageResponse;

public interface MessageService {

    SendMessageResponse sendMessage(final SendMessageRequest request);

    GetMessagesResponse getMessages();

}
