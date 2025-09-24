package com.ctu.bookstore.exception;

import com.ctu.bookstore.dto.respone.ApiRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    //bắt tất cả các exception còn lại
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiRespone> handlingException(Exception exception){
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiRespone.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone> handlingAppException(AppException exception){
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(exception.getErrorCode().getCode());
        apiRespone.setMessage(exception.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiRespone> handlingValidation(MethodArgumentNotValidException exception){
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        String enumKey = exception.getFieldError().getDefaultMessage();
        try {
             errorCode = ErrorCode.valueOf(enumKey);
        }catch (IllegalArgumentException e){

        }
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setCode(errorCode.getCode());
        apiRespone.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiRespone);
    }
}
