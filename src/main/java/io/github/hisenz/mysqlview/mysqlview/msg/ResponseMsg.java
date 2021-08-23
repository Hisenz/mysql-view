package io.github.hisenz.mysqlview.mysqlview.msg;

import lombok.Data;

/**
 * 统一接口响应
 */
@Data
public class  ResponseMsg {
    private String message;
    private boolean result;
    private Object data;

    private ResponseMsg(String message, boolean result, Object data) {
        this.data = data;
        this.result = result;
        this.message = message;
    }

    public static ResponseMsg create(String message, boolean result, Object data) {
        return new ResponseMsg(message, result, data);
    }

    public static ResponseMsg create(String message, boolean result) {
        return new ResponseMsg(message, result, null);
    }
}
