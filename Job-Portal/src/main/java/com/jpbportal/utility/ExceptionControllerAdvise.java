//package com.jpbportal.utility;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.ConstraintViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.time.LocalDateTime;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//public class ExceptionControllerAdvise {
//
//    private final ConstraintValidator constraintValidator;
//
//    public ExceptionControllerAdvise(ConstraintValidator constraintValidator) {
//        this.constraintValidator = constraintValidator;
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorInfo> generalException(Exception exception){
//        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
//        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
//    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception){
//        String msg = "";
//        if (exception instanceof MethodArgumentNotValidException manvException){
//            msg = manvException.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(", "));
//        } else {
//            ConstraintViolationException cvException = (ConstraintViolationException)exception;
//            msg = cvException.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
//        }
//        ErrorInfo errorInfo = new ErrorInfo(msg, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
//        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
//    }
//}
//
package com.jpbportal.utility;

import com.jpbportal.exception.JobPortalException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionControllerAdvise {

    @Autowired
    private Environment env;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalException(Exception exception){
        ErrorInfo errorInfo = new ErrorInfo(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JobPortalException.class)
    public ResponseEntity<ErrorInfo> generalException(JobPortalException exception){
        String msg = env.getProperty(exception.getMessage());
        ErrorInfo errorInfo = new ErrorInfo(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception){
        String msg = "";
        if (exception instanceof MethodArgumentNotValidException manvException){
            msg = manvException.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else if (exception instanceof ConstraintViolationException cvException){
            msg = cvException.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        }

        ErrorInfo errorInfo = new ErrorInfo(msg, HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}