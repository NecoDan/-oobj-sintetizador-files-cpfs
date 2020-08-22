package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;

public interface IArquivoService {

    void efetuarProcessamentosArquivos(List<Arquivo> arquivoList, PropriedadeArquivos propriedadeArquivos) throws ServiceException;

    List<File> recuperarArquivosFromPathEntrada(PropriedadeArquivos propriedadeArquivos) throws ServiceException;

    void salvarEmLote(List<Arquivo> arquivoList) throws ServiceException;

    void salvar(Arquivo arquivo) throws ServiceException;
}
