package com.young.eshop.inventory.result;

public enum ResultEnum {

    UNKNOWN_ERROR(-100, "未知异常"),
    VALID_ERROR(-101, "数据校验异常"),
    UPDATE_FAIL(101, "更新异常"),
    SYSTEM_FAIL(102, "系统异常"),
    NET_FAIL(103, "网络异常"),
    SQL_FAIL(104, "SQL异常"),

    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
