package com.example.rebookauthservice.exception;


import com.example.rebookauthservice.common.CommonResult;
import com.example.rebookauthservice.common.ResponseService;
import com.example.rebookauthservice.common.ResultCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CDuplicatedDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleDuplicatedException(CDuplicatedDataException ex){
        return ResponseService.getFailResult(ResultCode.DATA_DUPLICATED);
    }

    @ExceptionHandler(CMissingDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleMissingDataException(CMissingDataException ex){
        return ResponseService.getFailResult(ResultCode.DATA_MISSED);
    }

    @ExceptionHandler(AuthUserDataMissedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleMissingDataException(AuthUserDataMissedException ex){
        return ResponseService.getFailResult(ResultCode.AUTH_USER_DATA_MISSED);
    }

    @ExceptionHandler(CInvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleInvalidDataException(CInvalidDataException ex){
        return ResponseService.getFailResult(ResultCode.DATA_DUPLICATED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleUsernameNotFoundException(UsernameNotFoundException ex){
        return ResponseService.getFailResult(ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult handleBadCredentialsException(BadCredentialsException ex){
        return ResponseService.getFailResult(ResultCode.PASSWORD_UNMATCHED);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleRuntimeException(RuntimeException ex){
        return ResponseService.getFailResult(ex);
    }

}
