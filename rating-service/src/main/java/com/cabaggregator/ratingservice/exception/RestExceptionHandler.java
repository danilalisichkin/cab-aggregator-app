package com.cabaggregator.ratingservice.exception;

import com.cabaggregator.ratingservice.core.constant.ErrorCauses;
import com.cabaggregator.ratingservice.core.dto.error.ErrorResponse;
import com.cabaggregator.ratingservice.core.dto.error.MultiErrorResponse;
import com.cabaggregator.ratingservice.util.MessageBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestExceptionHandler {
    MessageBuilder messageBuilder;

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        e.getLocalizedMessage(),
                        messageBuilder.buildLocalizedMessage(ErrorCauses.NOT_FOUND)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(e.getErrorCauseKey())));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(e.getErrorCauseKey())));
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(ErrorCauses.CANT_READ_REQUEST),
                        messageBuilder.buildLocalizedMessage(ErrorCauses.BAD_REQUEST)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MultiErrorResponse> handleNoValidException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errorMap = new HashMap<>();

        getValidationErrors(errorMap, e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MultiErrorResponse(
                        messageBuilder.buildLocalizedMessage(ErrorCauses.VALIDATION),
                        errorMap));
    }

    @ExceptionHandler(ValidationErrorException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrorException(ValidationErrorException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(e.getErrorCauseKey())));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        e.getLocalizedMessage(),
                        messageBuilder.buildLocalizedMessage(ErrorCauses.FORBIDDEN)));
    }

    @ExceptionHandler(DataUniquenessConflictException.class)
    public ResponseEntity<ErrorResponse> handleDataIUniquenessConflictException(ParameterizedException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(e.getMessageKey(), e.getMessageArgs()),
                        messageBuilder.buildLocalizedMessage(e.getErrorCauseKey())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOtherException(Exception e) {
        log.error("internal server error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        messageBuilder.buildLocalizedMessage(ErrorCauses.CONTACT_DEVELOPERS),
                        messageBuilder.buildLocalizedMessage(ErrorCauses.INTERNAL)));
    }

    private void getValidationErrors(Map<String, List<String>> errorMap, MethodArgumentNotValidException e) {
        BiConsumer<String, String> addError = (field, message) ->
                errorMap.computeIfAbsent(field, k -> new ArrayList<>()).add(message);

        e.getBindingResult()
                .getFieldErrors().forEach(error ->
                        addError.accept(error.getField(), error.getDefaultMessage()));
    }
}