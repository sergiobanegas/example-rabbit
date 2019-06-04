package com.worldmessages.messagemanager.config;

import com.worldmessages.messagemanager.domain.DetailedError;
import com.worldmessages.messagemanager.domain.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {


    private static final String ACCEPTED_VALUES = "Accepted values: %s";
    private static final HttpStatus BAD_REQUEST_STATUS = HttpStatus.BAD_REQUEST;
    private static final String RECEIVER_FIELD = "receiver";


    @ExceptionHandler(InvalidDefinitionException.class)
    public ResponseEntity<ErrorResponse> handleConverterErrors(InvalidDefinitionException exception) {
        final DetailedError detailedError = new DetailedError(RECEIVER_FIELD, String.format(ACCEPTED_VALUES, exception.getCause().getLocalizedMessage()));
        final List<DetailedError> errors = Collections.singletonList(detailedError);
        return ResponseEntity.status(BAD_REQUEST_STATUS).body(new ErrorResponse(new Date(), BAD_REQUEST_STATUS.value(), errors));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status, final WebRequest request) {
        final List<DetailedError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new DetailedError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        final ErrorResponse errorResponse = new ErrorResponse(new Date(), status.value(), errors);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

}
