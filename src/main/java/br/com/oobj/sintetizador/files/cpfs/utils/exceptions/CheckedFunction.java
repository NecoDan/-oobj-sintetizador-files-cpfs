package br.com.oobj.sintetizador.files.cpfs.utils.exceptions;

@FunctionalInterface
public interface CheckedFunction<T, R> {
    R apply(T t) throws Exception;
}
