package com.cabaggregator.rideservice.exception;

import com.cabaggregator.rideservice.core.constant.MessageKeys;
import com.cabaggregator.rideservice.core.dto.error.ErrorResponse;
import com.cabaggregator.rideservice.core.dto.error.MultiErrorResponse;
import com.cabaggregator.rideservice.util.MessageBuilder;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final MessageBuilder messageBuilder;

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        e.getLocalizedMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.NOT_FOUND, null)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotResourceFoundException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.NOT_FOUND, null)));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestParameterException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        e.getMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.BAD_REQUEST, null)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.BAD_REQUEST, null)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.CANT_READ_REQUEST, null),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.BAD_REQUEST, null)));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<MultiErrorResponse> handleNoValidException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errorMap = new HashMap<>();

        getValidationErrors(errorMap, e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MultiErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.VALIDATION, null),
                        errorMap));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        e.getMessage(),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.UNIQUENESS_CONFLICT, null)));
    }

    @ExceptionHandler(DataUniquenessConflictException.class)
    public ResponseEntity<ErrorResponse> handleDataIUniquenessConflictException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.UNIQUENESS_CONFLICT, null)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception e) {
        log.error("internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.CONTACT_DEVELOPERS, null),
                        messageBuilder.buildLocalizedMessage(MessageKeys.ErrorCauses.INTERNAL, null)));
    }

    private void getValidationErrors(Map<String, List<String>> errorMap, Exception e) {
        BiConsumer<String, String> addError = (field, message) ->
                errorMap.computeIfAbsent(field, k -> new ArrayList<>()).add(message);

        if (e instanceof MethodArgumentNotValidException validationEx) {
            validationEx.getBindingResult()
                    .getFieldErrors().forEach(error ->
                            addError.accept(error.getField(), error.getDefaultMessage()));
        } else if (e instanceof ConstraintViolationException constraintEx) {
            constraintEx.getConstraintViolations()
                    .forEach(violation ->
                            addError.accept(violation.getPropertyPath().toString(), violation.getMessage()));
        }
    }
}
