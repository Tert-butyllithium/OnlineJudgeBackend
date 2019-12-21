package edu.sustech.oj_server.util;

import java.util.List;


public class ReturnType<T> {
    String error;
    T data;

    public ReturnType(T object) {
        error=null;
        this.data = object;
//        this.total=total;
    }

    public ReturnType(String error, T data) {
        this.error = error;
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public T getData() {
        return data;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }
}
