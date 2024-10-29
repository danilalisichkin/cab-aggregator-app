package com.cabaggregator.driverservice.controllers;

import com.cabaggregator.driverservice.core.constants.MessageKeys;
import com.cabaggregator.driverservice.core.dto.error.ErrorResponse;
import com.cabaggregator.driverservice.core.dto.error.MultiErrorResponse;
import com.cabaggregator.driverservice.exceptions.BadRequestException;
import com.cabaggregator.driverservice.exceptions.DataUniquenessConflictException;
import com.cabaggregator.driverservice.exceptions.ResourceNotFoundException;
import com.cabaggregator.driverservice.exceptions.ServerErrorException;
import com.cabaggregator.driverservice.utils.MessageBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MessageBuilder messageBuilder;

    @Autowired
    public RestExceptionHandler(MessageBuilder messageBuilder) {
        this.messageBuilder = messageBuilder;
    }

    @ExceptionHandler({EntityNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        e.getLocalizedMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_NOT_FOUND, null)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleNotResourceFoundException(ServerErrorException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_NOT_FOUND, null)));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingRequestParameterException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        e.getMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_BAD_REQUEST, null)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(ServerErrorException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_BAD_REQUEST, null)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CANT_READ_REQUEST, null),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_BAD_REQUEST, null)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleNoValidException(MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MultiErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_VALIDATION, null),
                        errorMap));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        e.getMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_UNIQUENESS_CONFLICT, null)));
    }

    @ExceptionHandler(DataUniquenessConflictException.class)
    public ResponseEntity<Object> handleDataIUniquenessConflictException(ServerErrorException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_UNIQUENESS_CONFLICT, null)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception e) {
        logger.error("internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_IF_PERSISTS_CONTACT_DEVELOPERS, null),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_INTERNAL, null)));
    }
}