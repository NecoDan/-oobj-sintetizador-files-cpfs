package br.com.oobj.sintetizador.files.cpfs.utils.exceptions;

@FunctionalInterface
public interface FunctionWithException<T, R, E extends Exception> {
    R apply(T t) throws E;
}
