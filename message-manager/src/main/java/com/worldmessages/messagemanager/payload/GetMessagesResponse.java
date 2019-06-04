package com.worldmessages.messagemanager.payload;

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
public class GetMessagesResponse {

    private List<Message> content;

}
