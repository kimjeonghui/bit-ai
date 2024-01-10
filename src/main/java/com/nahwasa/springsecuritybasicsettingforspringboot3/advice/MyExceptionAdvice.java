package com.nahwasa.springsecuritybasicsettingforspringboot3.advice;

import com.nahwasa.springsecuritybasicsettingforspringboot3.annotation.MyErrorLog;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.ResponseDTO;
import com.nahwasa.springsecuritybasicsettingforspringboot3.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringTokenizer;

@Slf4j
@RestControllerAdvice
public class MyExceptionAdvice {

    @MyErrorLog
    @ExceptionHandler(Exception400.class)
    public ResponseEntity<?> badRequest(Exception400 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLog
    @ExceptionHandler(Exception401.class)
    public ResponseEntity<?> unAuthorized(Exception401 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLog
    @ExceptionHandler(Exception403.class)
    public ResponseEntity<?> forbidden(Exception403 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLog
    @ExceptionHandler(Exception404.class)
    public ResponseEntity<?> notFound(Exception404 e){
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLog
    @ExceptionHandler(Exception500.class)
    public ResponseEntity<?> serverError(Exception500 e){
//        Sentry.captureException(e); checkpoint, 서버 배포전 해제
        return new ResponseEntity<>(e.body(), e.status());
    }

    @MyErrorLog
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationError(ConstraintViolationException e) {
        StringTokenizer st = new StringTokenizer(e.getMessage());
        String key = st.nextToken();
        String value = e.getMessage().replaceFirst(key, "");
        Exception400 exception400 = new Exception400(key, value);
        return new ResponseEntity<>(exception400.body(), exception400.status());
    }

    @MyErrorLog
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unknownServerError(Exception e){
        ResponseDTO<String> responseDTO = new ResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, "unknownServerError", e.getMessage());
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
