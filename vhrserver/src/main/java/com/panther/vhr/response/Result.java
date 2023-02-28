package com.panther.vhr.response;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 21:23
 **/
public class Result {
    private Integer status;
    private String msg;
    private Object data;

    public Result(){

    }

    public static Result build() {
        return new Result();
    }

    public static Result ok(String msg) {
        return new Result(200, msg, null);
    }

    public static Result ok(String msg, Object data) {
        return new Result(200, msg, data);
    }

    public static Result error(String msg) {
        return new Result(500, msg, null);
    }

    private Result(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public Result setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public Result setData(Object data) {
        this.data = data;
        return this;
    }
}
