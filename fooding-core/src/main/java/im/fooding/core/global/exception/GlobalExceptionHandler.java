package im.fooding.core.global.exception;

import im.fooding.core.global.infra.slack.SlackClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final SlackClient slackClient;

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final ApiException e, HttpServletRequest request) {
        String url = getRequestFullUrl(request);
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), url, request.getMethod());
        if (e.isNotifyTarget()) {
            sendError(errorResponse, e.getMessage(), url);
        }
        return ResponseEntity
                .status(e.getErrorCode().getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED, getRequestFullUrl(request), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> NoHandlerFoundException(final NoHandlerFoundException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_FOUND, getRequestFullUrl(request), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.NOT_FOUND.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> AccessDeniedException(final AccessDeniedException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ACCESS_DENIED_EXCEPTION, getRequestFullUrl(request), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> MethodArgumentNotValidException(final MethodArgumentNotValidException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_ARGUMENT_NOT_VALID, getRequestFullUrl(request), request.getMethod());
        errorResponse.setDetailedErrors(new ArrayList<>());
        errorResponse.getDetailedErrors().addAll(
                e.getBindingResult().getFieldErrors()
                        .stream().map(x -> DetailedError.builder()
                                .message(x.getDefaultMessage())
                                .location(x.getField())
                                .build()
                        ).toList()
        );
        return ResponseEntity
                .status(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> HttpMessageNotReadableException(final HttpMessageNotReadableException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_READABLE, getRequestFullUrl(request), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.NOT_READABLE.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> MissingServletRequestParameterException(final MissingServletRequestParameterException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.MISSING_REQUEST_PARAMETER, getRequestFullUrl(request), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.MISSING_REQUEST_PARAMETER.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<ErrorResponse> HttpMessageConversionException(final HttpMessageConversionException e, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_READABLE, getRequestFullUrl(request), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.NOT_READABLE.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler({
            DuplicateKeyException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class
    })
    protected ResponseEntity<ErrorResponse> DatabaseException(final Exception e, HttpServletRequest request) {
        String url = getRequestFullUrl(request);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.DATABASE_EXCEPTION, url, request.getMethod());
        sendError(errorResponse, e.getMessage(), url);
        return ResponseEntity
                .status(ErrorCode.DATABASE_EXCEPTION.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> Exception(final Exception e, HttpServletRequest request) {
        String url = getRequestFullUrl(request);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, url, request.getMethod());
        e.printStackTrace();
        sendError(errorResponse, e.getMessage(), url);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(errorResponse);
    }

    private void sendError(ErrorResponse errorResponse, String message, String url) {
        slackClient.sendErrorMessage(errorResponse, message, url);
    }

    private String getRequestFullUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null) {
            url += "?" + queryString;
        }
        return url;
    }
}
