package br.com.oobj.sintetizador.files.cpfs.utils.exceptions;

import java.util.function.Function;

public final class ExceptionLambdaUtil {

    private ExceptionLambdaUtil() {
    }

    public static <T, R> Function<T, R> wrap(CheckedFunction<T, R> checkedFunction) {
        return t -> {
            try {
                return checkedFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
