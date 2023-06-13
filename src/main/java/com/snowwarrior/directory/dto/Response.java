package com.snowwarrior.directory.dto;

import java.util.HashMap;

public class Response<T> {
    private String status;
    private String message;
    private HashMap<String, T> data;
    public Response(String status, String message, HashMap<String, T> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
