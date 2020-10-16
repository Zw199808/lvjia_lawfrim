package com.xinou.lawfrim.common.config;

import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.BusException;
import com.xinou.lawfrim.common.util.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    /**
     * 捕获 BusException
     * @param e BusException
     * @return APIResponse
     */
    @ExceptionHandler(BusException.class)
    @ResponseBody
    public APIResponse customException(BusException e) {
        log.error("catch exception : {}\r\nexception: ", e.getMessage(),e);
        return new APIResponse(e.getCode(),e.getMessage());
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public APIResponse errorHandler(Exception ex) {
        log.error("接口出现严重异常：{}", ex.getMessage(),ex);
        return new APIResponse(Config.RE_CODE_DATABASE_ERROR,Config.RE_MSG_DATABASE_ERROR);
    }

    /**
     * 参数校验
     * @param e 异常
     * @return APIResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public APIResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        if(fieldError == null){
            return new APIResponse(Config.RE_CODE_DATABASE_ERROR,Config.RE_MSG_DATABASE_ERROR);
        }
        return new APIResponse(Config.RE_CODE_DATABASE_ERROR,fieldError.getDefaultMessage());
    }
}
