package com.worldmessages.messagemanager.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class Message {

    public Long id;

    private String body;

    private String receiver;

    private Timestamp createdDate;

}
