package com.worldmessages.messagemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private Date timestamp;

    private int status;

    private List<DetailedError> errors;

}
