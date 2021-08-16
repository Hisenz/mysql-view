package io.github.hisenz.mysqlview.mysqlview.advice;

import io.github.hisenz.mysqlview.mysqlview.costant.ExceptionConstants;
import io.github.hisenz.mysqlview.mysqlview.exception.DataSourceConnectException;
import io.github.hisenz.mysqlview.mysqlview.msg.ResponseMsg;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = DataSourceConnectException.class)
    public ResponseMsg SQLNonTransientConnectionException(DataSourceConnectException exception) {
        return ResponseMsg.create(ExceptionConstants.DATA_SOURCE_CONNECT_EXCEPTION, false);
    }
}
