package com.snowwarrior.directory.dto;

import java.util.HashMap;

public class Response<T> {
    private int status;
    private String message;
    private HashMap<String, T> data;
    public Response(int status, String message, HashMap<String, T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, T> getData() {
        return data;
    }

    public void setData(HashMap<String, T> data) {
        this.data = data;
    }
}
