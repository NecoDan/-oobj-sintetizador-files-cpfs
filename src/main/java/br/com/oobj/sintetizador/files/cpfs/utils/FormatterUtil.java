package br.com.oobj.sintetizador.files.cpfs.utils;

import java.util.Objects;

public final class FormatterUtil {

    private FormatterUtil() {
    }

    public static String retirarCaracteresNaoNumericos(String value) {
        return (Objects.isNull(value) || value.isEmpty()) ? "" : value.replaceAll("[^\\d]", "");
    }
}
