package br.com.oobj.sintetizador.files.cpfs.validation;

import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;

public interface IValidadorProcessamentoService {

    void validarPropriedadesArquivo(PropriedadeArquivos propriedadeArquivos) throws ServiceException;

    void validarContemArquivosObtidosFileDiretorioPadraoEntrada(List<File> fileInputDatList) throws RepositoryException;

    void validarFileEstahValidoParaObterLinhasRegistros(File file) throws ServiceException;

    boolean isNotContainsArquivosDiretorioPadraoEntrada(File filePathInput);
}
