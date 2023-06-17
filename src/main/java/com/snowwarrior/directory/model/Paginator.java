package com.snowwarrior.directory.model;

public class Paginator<T> {
    public Long totalCount;
    public T[] data;

    public Paginator(Long totalCount, T[] data) {
        this.totalCount = totalCount;
        this.data = data;
    }
}
