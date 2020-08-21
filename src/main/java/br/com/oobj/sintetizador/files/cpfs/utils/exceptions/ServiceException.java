package br.com.oobj.sintetizador.files.cpfs.utils.exceptions;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ServiceException extends Exception {

    private static final String MSG_VALIDACAO_DEFAULT = "Validação serviço - ";

    public ServiceException(String s) {
        super(MSG_VALIDACAO_DEFAULT.concat(s));
    }

    public ServiceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    public ServiceException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }

    public static String getMsgValidacaoDefault() {
        return MSG_VALIDACAO_DEFAULT;
    }
}
