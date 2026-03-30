package br.com.c137.project.core.mappers;

public interface MapperPut<T, U, V> {
    U mapper(T u, V v);
}
