package com.cabaggregator.passengerservice.exception;

import com.cabaggregator.passengerservice.core.constant.MessageKeys;
import com.cabaggregator.passengerservice.core.dto.error.ErrorResponse;
import com.cabaggregator.passengerservice.core.dto.error.MultiErrorResponse;
import com.cabaggregator.passengerservice.util.MessageBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final MessageBuilder messageBuilder;

    @ExceptionHandler({EntityNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        e.getLocalizedMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_NOT_FOUND, null)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotResourceFoundException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_NOT_FOUND, null)));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParameterException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        e.getMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_BAD_REQUEST, null)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_BAD_REQUEST, null)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CANT_READ_REQUEST, null),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_BAD_REQUEST, null)));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<MultiErrorResponse> handleNoValidException(Exception e) {
        Map<String, String> errorMap = new HashMap<>();

        if (e instanceof MethodArgumentNotValidException validationEx) {
            validationEx.getBindingResult().getFieldErrors().forEach(error ->
                    errorMap.put(error.getField(), error.getDefaultMessage())
            );
        } else if (e instanceof ConstraintViolationException constraintEx) {
            constraintEx.getConstraintViolations().forEach(violation ->
                    errorMap.put(violation.getPropertyPath().toString(), violation.getMessage())
            );
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MultiErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_VALIDATION, null),
                        errorMap));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        e.getMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_UNIQUENESS_CONFLICT, null)));
    }

    @ExceptionHandler(DataUniquenessConflictException.class)
    public ResponseEntity<ErrorResponse> handleDataIUniquenessConflictException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_UNIQUENESS_CONFLICT, null)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception e) {
        log.error("internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_IF_PERSISTS_CONTACT_DEVELOPERS, null),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ERROR_CAUSE_INTERNAL, null)));
    }
}
