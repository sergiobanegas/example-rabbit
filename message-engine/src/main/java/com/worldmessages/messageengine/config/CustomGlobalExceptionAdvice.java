package com.worldmessages.messageengine.config;

import com.worldmessages.messageengine.domain.DetailedError;
import com.worldmessages.messageengine.domain.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionAdvice extends ResponseEntityExceptionHandler {

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
