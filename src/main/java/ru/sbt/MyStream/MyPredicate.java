package ru.sbt.MyStream;

@FunctionalInterface
public interface MyPredicate<T> {
    boolean test(T val);
}
