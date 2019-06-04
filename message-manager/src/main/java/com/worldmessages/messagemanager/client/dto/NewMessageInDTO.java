package com.worldmessages.messagemanager.client.dto;

import com.worldmessages.messagemanager.domain.ReceiverZone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class NewMessageInDTO {

    @NotEmpty
    private String body;

    @NotNull
    private ReceiverZone receiver;

}
