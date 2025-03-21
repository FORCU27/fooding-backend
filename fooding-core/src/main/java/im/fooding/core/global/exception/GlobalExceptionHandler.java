package im.fooding.core.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final ApiException e, HttpServletRequest request) {
        log.error("handleCustomException: {}", e.getErrorCode());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(e.getErrorCode().getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> HttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> NoHandlerFoundException(final NoHandlerFoundException e, HttpServletRequest request) {
        log.error("NoHandlerFoundException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_FOUND, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.NOT_FOUND.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> AccessDeniedException(final AccessDeniedException e, HttpServletRequest request) {
        log.error("AccessDeniedException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ACCESS_DENIED_EXCEPTION, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> MethodArgumentNotValidException(final MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(400, "0005", e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> HttpMessageNotReadableException(final HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("HttpMessageNotReadableException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_READABLE, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.NOT_READABLE.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ErrorResponse> MissingServletRequestParameterException(final MissingServletRequestParameterException e, HttpServletRequest request) {
        log.error("MissingServletRequestParameterException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.MISSING_REQUEST_PARAMETER, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.MISSING_REQUEST_PARAMETER.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    protected ResponseEntity<ErrorResponse> HttpMessageConversionException(final HttpMessageConversionException e, HttpServletRequest request) {
        log.error("HttpMessageConversionException: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.NOT_READABLE, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.NOT_READABLE.getStatus().value())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> Exception(final Exception e, HttpServletRequest request) throws IOException {
        log.error("Exception: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, request.getRequestURI(), request.getMethod());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
                .body(errorResponse);
    }
}
