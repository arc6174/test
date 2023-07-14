package com.softeem.crm.exceptions;

//无权访问异常
public class NoAuthException extends RuntimeException{
    private Integer code=300;
    private String msg="访问不足!";


    public NoAuthException() {
        super("访问不足!");
    }

    public NoAuthException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public NoAuthException(Integer code) {
        super("访问不足!");
        this.code = code;
    }

    public NoAuthException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
