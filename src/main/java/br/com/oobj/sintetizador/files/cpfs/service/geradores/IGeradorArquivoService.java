package br.com.oobj.sintetizador.files.cpfs.service.geradores;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;

public interface IGeradorArquivoService {

    void processar(PropriedadeArquivos propriedadeArquivos) throws ServiceException;

    void efetuarProcessamentos(PropriedadeArquivos propriedadeArquivos, List<File> fileList) throws ServiceException;

    List<Arquivo> obterListaArquivosAProcessarFrom(File file, PropriedadeArquivos propriedadeArquivos) throws ServiceException;
}
