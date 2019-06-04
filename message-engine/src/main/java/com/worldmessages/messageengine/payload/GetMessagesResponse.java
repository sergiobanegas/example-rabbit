package com.worldmessages.messageengine.payload;

import com.worldmessages.messageengine.entity.Message;
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
