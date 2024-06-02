package com.vdt.fosho.exception;

import com.vdt.fosho.utils.JSendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public JSendResponse<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        return JSendResponse.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public JSendResponse<HashMap<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // Handle DTO validation errors
        HashMap<String, String> messages = new HashMap<>();
        for (int i = 0; i < e.getAllErrors().size(); i++) {
            messages.put(e.getFieldErrors().get(i).getField(), e.getAllErrors().get(i).getDefaultMessage());
        }
        return JSendResponse.fail(messages);
    }

    // TODO: Handle SQL exceptions
    @ExceptionHandler(SQLException.class)
    public JSendResponse<Object> handleSQLException(SQLException e) {
        return JSendResponse.error(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public JSendResponse<Object> handleAuthenticationException(AuthenticationException e) {
        return JSendResponse.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public JSendResponse<Object> handleException(Exception e) {
       //return JSendResponse.error(e.getMessage());
        return JSendResponse.error("Something went wrong or your data is duplicated. Please try again later 🌠.");
    }
}
