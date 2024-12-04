package ch.admin.bar.siard2.cmd.utils;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingBiFunction<T, U, R> {
    R apply(T t, U u) throws IOException;
}
