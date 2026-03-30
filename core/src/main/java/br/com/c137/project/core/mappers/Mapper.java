package br.com.c137.project.core.mappers;

public interface Mapper<T, U> {
    public U mapper (T t);
}
