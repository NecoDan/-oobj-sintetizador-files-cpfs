package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.nio.file.Path;

public interface IProcessarArquivoService {

    void efetuarProcessamentoPor(String pathEntrada, String pathDestino, String pathDestinoArquivoAMover) throws ServiceException;

    void efetuarProcessamento(File pathEntrada, File pathDestino, File pathDestinoArquivoAMover) throws ServiceException;

    void efetuarProcessamentosPor(Path pathEntrada, Path pathDestino, Path pathDestinoArquivoAMover) throws ServiceException;
}
