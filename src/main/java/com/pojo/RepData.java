package com.pojo;

import lombok.Data;

@Data
public class RepData<T> {
    private int code;
    private String msg;
    private T currData;

    public RepData(int code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.currData = data;
    }
    public RepData(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static RepData success(Object t){
        return new RepData(Rep.SUCCES_CODE, Rep.SUCCES_MSG, t);
    }
    public static RepData success(String msg){
        return new RepData(Rep.SUCCES_CODE, msg, null);
    }
    public static RepData erro(String msg){
        return new RepData(Rep.ERRO_CODE, msg);
    }
}
