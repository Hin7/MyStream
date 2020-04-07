package ru.sbt.MyStream;

@FunctionalInterface
public interface MyFunction<T, R> {
    R apply(T val);
}
