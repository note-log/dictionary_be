package com.snowwarrior.directory.model;

public class Paginator<T> {
    public Long cursor;
    public T[] data;
    public Paginator(Long cursor, T[] data) {
        this.cursor = cursor;
        this.data = data;
    }
}
