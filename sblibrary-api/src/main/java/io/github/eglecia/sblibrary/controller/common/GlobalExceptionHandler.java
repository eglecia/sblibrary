package io.github.eglecia.sblibrary.controller.common;

import io.github.eglecia.sblibrary.controller.dto.ResponseError;
import io.github.eglecia.sblibrary.controller.dto.CFieldError;
import io.github.eglecia.sblibrary.exceptions.InvalidFieldException;
import io.github.eglecia.sblibrary.exceptions.OperationNotPermitted;
import io.github.eglecia.sblibrary.exceptions.RegistryDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

// Tratamento global de exceptions. O spring intercepta as exceptions lançadas pelos controllers
// e as direciona para essa classe
// Cada método é responsável por tratar um tipo específico de exception
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

    @ExceptionHandler(RegistryDuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleRegistryDuplicatedException(RegistryDuplicatedException e){
        return ResponseError.conflictResponse(e.getMessage());
    }

    @ExceptionHandler(OperationNotPermitted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleOperationNotPermitted(OperationNotPermitted e){
        return ResponseError.defaultResponse(e.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handleInvalidFieldException(InvalidFieldException e){
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Invalid field: " + e.getFieldName() + " - " + e.getMessage(),
                List.of(new CFieldError(e.getFieldName(), e.getMessage()))
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleRuntimeException(RuntimeException e) {
        return new ResponseError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error! Contact the system administrator.",
                List.of()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handleAccessDeniedException(AccessDeniedException e){
        return new ResponseError(HttpStatus.FORBIDDEN.value(), "Access Denied", List.of());
    }
}
