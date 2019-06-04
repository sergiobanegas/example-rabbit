package com.worldmessages.messagemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DetailedError {

    private String field;

    private String message;

}
