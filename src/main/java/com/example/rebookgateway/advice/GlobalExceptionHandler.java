package com.example.rebookgateway.advice;

import com.example.rebookgateway.common.CommonResult;
import com.example.rebookgateway.common.ResponseService;
import com.example.rebookgateway.common.ResultCode;
import com.example.rebookgateway.exceptions.CDuplicatedException;
import com.example.rebookgateway.exceptions.CInvalidDataException;
import com.example.rebookgateway.exceptions.CMissingDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(CDuplicatedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleDuplicatedException(CDuplicatedException ex){
        return ResponseService.getFailResult(ResultCode.DATA_DUPLICATED);
    }

    @ExceptionHandler(CMissingDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleInvalidDataException(CMissingDataException ex){
        return ResponseService.getFailResult(ResultCode.DATA_MISSED);
    }

    @ExceptionHandler(CInvalidDataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleInvalidDataException(CInvalidDataException ex){
        return ResponseService.getFailResult(ResultCode.DATA_INVALID);
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleRestClientException(RestClientException ex){
        return ResponseService.getFailResult(ex);
    }
}
