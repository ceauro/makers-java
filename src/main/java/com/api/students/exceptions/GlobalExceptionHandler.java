package com.api.students.exceptions;

import com.api.students.models.responses.ResponseError;
import com.api.students.models.responses.ResponseErrors;
import com.api.students.models.responses.ResponseGeneral;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseGeneral> handleValidationException(MethodArgumentNotValidException ex) {
        List<ResponseError> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .map(ResponseError::new)
                .toList();

        if(errors.size() == 1){
            return ResponseEntity.badRequest().body(errors.get(0));
        }

        return ResponseEntity.badRequest().body(new ResponseErrors(errors));
    }

    @ExceptionHandler(StudentException.class)
    public ResponseEntity<ResponseGeneral> handleMissingConfigurationException(StudentException ex) {

        HttpStatus status;

        if(ex.isConflict()){
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(new ResponseError(ex.getMessage()), status);
    }
}
