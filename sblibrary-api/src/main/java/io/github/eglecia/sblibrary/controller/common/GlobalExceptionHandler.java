package io.github.eglecia.sblibrary.controller.common;

import io.github.eglecia.sblibrary.controller.dto.ResponseError;
import io.github.eglecia.sblibrary.controller.dto.CFieldError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseError handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();

        List<CFieldError> errorList = fieldErrors
                .stream()
                .map(fe -> new CFieldError(fe.getField(), fe.getDefaultMessage()))
                .toList();

        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error",
                errorList);
    }
}
