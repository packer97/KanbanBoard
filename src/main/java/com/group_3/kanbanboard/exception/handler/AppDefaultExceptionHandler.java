package com.group_3.kanbanboard.exception.handler;

import com.group_3.kanbanboard.exception.ExceptionResponse;
import com.group_3.kanbanboard.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Date;


/**
 * Default Exception handler in Application
 */
@ControllerAdvice
public class AppDefaultExceptionHandler {

    /**
     * Handle custom project {@link ResourceNotFoundException} if missing resource requested.
     * Set the {@link HttpStatus} code to 404.
     *
     * @param e - {@link ResourceNotFoundException} throw then missing resource requested.
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.
     */
    //Запрошен отсутствующий ресурс(Task, Project, User, Release и т.д )  можно раскидать по отдельным классам
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> exceptionHandler(ResourceNotFoundException e) {
        // лог
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle Spring Security {@link AuthenticationException} if authentication error.
     * Set the {@link HttpStatus} code to 401.
     *
     * @param e - {@link AuthenticationException} throw then authentication error.
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.
     */

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponse> exceptionHandler(AuthenticationException e) {
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle Spring Security {@link AccessDeniedException} if was an attempt to access a limited to access resource.
     * Set the {@link HttpStatus} code to 401.
     *
     * @param e - {@link AccessDeniedException} throw when there was an attempt to access a limited to access resource.
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.
     */

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ExceptionResponse> exceptionHandler(AccessDeniedException e) {
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

    }

    /**
     * Handle {@link IllegalArgumentException} if incorrect method parameters in runtime.
     * Set the {@link HttpStatus} code to 405.
     *
     * @param e - {@link IllegalArgumentException} throw when incorrect method parameters in runtime.
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.
     */

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ExceptionResponse> exceptionHandler(IllegalArgumentException e) {
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new  ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handle {@link IOException} for I/O errors.
     * Set the {@link HttpStatus} code to 400.
     *
     * @param e - {@link IOException} throw then I/O errors
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.
     */

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> exceptionHandler(IOException e) {
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle {@link RuntimeException} then errors occurrence in runtime(unchecked).
     * Set the {@link HttpStatus} code to 400.
     *
     * @param e - {@link RuntimeException} throw then errors occurrence in runtime
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.     *
     */

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> exceptionHandler(RuntimeException e) {
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle {@link Exception}} for all other errors.
     * Set the {@link HttpStatus} code to 400.
     *
     * @param e - {@link Exception} called then all other errors
     * @return {@link ResponseEntity} wrapped {@link ExceptionResponse}.
     */

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
        ExceptionResponse response = new ExceptionResponse(new Date(), e.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}




