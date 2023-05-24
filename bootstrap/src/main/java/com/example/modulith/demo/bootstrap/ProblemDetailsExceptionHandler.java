package com.example.modulith.demo.bootstrap;

import java.net.URI;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ProblemDetailsExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Ошибка валидации");
        problemDetail.setDetail("Параметры запроса не прошли валидацию");
        problemDetail.setProperty(
            "violations",
            ex.getBindingResult().getFieldErrors().stream().map(e -> new Violation(e.getField(), e.getDefaultMessage()))
        );
        headers = HttpHeaders.writableHttpHeaders(headers);
        headers.add("Content-Type", "application/problem+json");
        return super.createResponseEntity(problemDetail, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> createResponseEntity(
        Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request
    ) {
        if (body instanceof ProblemDetail b) {
            String traceId = MDC.get("traceId");
            if (traceId != null) {
                b.setInstance(URI.create("urn:traceId:" + traceId));
            }
        }
        return super.createResponseEntity(body, headers, statusCode, request);
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail unhandledException(Exception e) {
        LOGGER.warn("Необработанное исключение", e);
        ProblemDetail problemDetail =
            ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Необработанное исключение");
        problemDetail.setTitle("Необработанное исключение");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create("urn:traceId:" + MDC.get("traceId")));
        return problemDetail;
    }
}
