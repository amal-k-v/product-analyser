//package com.machinetest.product.analyse.java.ms.core;
//
//import com.machinetest.product.analyse.java.ms.exception.BussinessException;
//import com.machinetest.product.analyse.java.ms.model.ErrorResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//@ControllerAdvice()
//public class GlobalExceptionHandler {
//
////    @ExceptionHandler(BussinessException.class)
////    public ResponseEntity<ErrorResponse> handleAllExceptions(BussinessException ex, WebRequest request) {
////
////        ErrorResponse errorResponse = new ErrorResponse(
////                LocalDateTime.now(),
////                400,
////                HttpStatus.BAD_REQUEST.getReasonPhrase(),
////                ex.getMsg(),
////                request.getDescription(false).replace("uri=", "")
////        );
////
////        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
////    }
//
//
//
//}
