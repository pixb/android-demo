package com.pix.http;

import java.util.HashMap;
import java.util.Map;

public class ErrorObject {

    /***服务端返回的错误吗***/
    public static final int SERVER_BLACK_LIST_ERROR=200400;

    /******/

    public static final int ERROR_CODE = 1;
    public static final int ERROR_NET_FAIL = 2;
    public static final int ERROR_DATA_PARSE =3;
    public static final int ERROR_DATA_RESOLVE = 4;
    public static final int ERROR_DATA_INTERFACE =5;
    public static final int ERROR_SERVER_ERROR = 6;
    private static final ErrorObject baseError = build(ERROR_CODE, "基础错误类型");
    private static final ErrorObject netError = build(ERROR_NET_FAIL, "网络错误");
    private static final ErrorObject parseError = build(ERROR_DATA_PARSE, "数据解析错误");
    private static final ErrorObject resolveError = build(ERROR_DATA_RESOLVE, "数据处理错误");
    private static final ErrorObject dataInterError = build(ERROR_DATA_INTERFACE, "数据接口错误");
    private static final ErrorObject serverError = build(ERROR_SERVER_ERROR, "服务器错误");
    private static final Map<Integer, ErrorObject> collections = new HashMap<>();

    static {
        collections.put(ERROR_CODE, baseError);
        collections.put(ERROR_NET_FAIL, netError);
        collections.put(ERROR_DATA_PARSE, parseError);
        collections.put(ERROR_DATA_RESOLVE, resolveError);
        collections.put(ERROR_DATA_INTERFACE, dataInterError);
        collections.put(ERROR_SERVER_ERROR, serverError);
    }

    private int code;
    private String message;

    public ErrorObject(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getErrorID() {
        return this.code;
    }

    public static ErrorObject parseError(int code) {
        ErrorObject errorObject = collections.get(code);
        if (errorObject == null) {
            errorObject = baseError;
        }
        return errorObject;
    }
    public static ErrorObject build(int code){
        return build(code,"未知错误描述，请询问服务端");
    }
    public  static ErrorObject build(int code, String message) {
        return new ErrorObject(code, message);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "ErrorObject{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
