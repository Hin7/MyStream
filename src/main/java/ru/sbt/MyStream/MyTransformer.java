package ru.sbt.MyStream;

@FunctionalInterface
public interface MyTransformer<T> {
    T transform(T val);
}
