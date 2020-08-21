package br.com.oobj.sintetizador.files.cpfs.utils.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RepositoryException extends Exception {
    public RepositoryException(String s) {
        super(s);
    }
}
