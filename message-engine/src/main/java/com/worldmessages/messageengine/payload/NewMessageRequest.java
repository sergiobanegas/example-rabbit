package com.worldmessages.messageengine.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewMessageRequest {

    @NotEmpty
    private String body;

    @NotEmpty
    private String receiver;

}
