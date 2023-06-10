package com.snowwarrior.directory.util;

import com.snowwarrior.directory.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;

public class ResponseEntityHelper {
    private ResponseEntityHelper() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    public static <T> ResponseEntity<Response<T>> ok(String message, String key, T data) {
        var map = new HashMap<String, T>();
        map.put(key, data);
        return new ResponseEntity<>(new Response<>(1, message, map), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Response<T>> ok(String message, String key, T data, MultiValueMap<String, String> headers) {
        var map = new HashMap<String, T>();
        map.put(key, data);
        return new ResponseEntity<>(new Response<>(1, message, map), headers, HttpStatus.OK);
    }

    public static <T> ResponseEntity<Response<T>> ok(String message) {
        return new ResponseEntity<>(new Response<>(1, message, new HashMap<>()), HttpStatus.OK);
    }

    public static <T> ResponseEntity<Response<T>> fail(HttpStatus httpStatus) {
        return new ResponseEntity<>(null, httpStatus);
    }
}
