package br.com.c137.project.core.mappers;

public interface MapperPut<T, U> {
    U mapper(T u, U v);
}
