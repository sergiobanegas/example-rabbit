package com.worldmessages.messagemanager.client.dto;

import com.worldmessages.messagemanager.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetMessagesOutDTO {

    private List<Message> content;

}
