package com.worldmessages.messagemanager.payload;

import com.worldmessages.messagemanager.domain.ReceiverZone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {

    @NotEmpty
    private String body;

    @NotNull
    private ReceiverZone receiver;

}
