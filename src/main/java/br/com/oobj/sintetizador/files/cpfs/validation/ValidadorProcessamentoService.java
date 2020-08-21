package br.com.oobj.sintetizador.files.cpfs.validation;

import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil.isFileDiretorioInValido;

public class ValidadorProcessamentoService implements IValidadorProcessamentoService {

    @Override
    public void validarPropriedadesArquivo(PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        if (Objects.isNull(propriedadeArquivos))
            throw new ServiceException("As propriedades de execução do processamentos dos arquivos encontra-se inválido e/ou inexistente {NULL}.");

        if (isFileDiretorioInValido(propriedadeArquivos.getPathEntrada()))
            throw new ServiceException("Não existem arquivo(s) a serem lido(s) no diretório padrão de entrada dos arquivos. Pasta vazia e/ou inexistente(s) {NULL}.");

        if (isFileDiretorioInValido(propriedadeArquivos.getPathDestinoPadrao()))
            throw new ServiceException("Diretório a conter os arquivos de destino encontra-se inv[alido e/ou inexistente(s) {NULL}.");

        if (isFileDiretorioInValido(propriedadeArquivos.getPathDestinoArquivoAMover()))
            throw new ServiceException("Diretório a conter os arquivos a serem movidos encontra-se inv[alido e/ou inexistente(s) {NULL}.");

        if (Objects.isNull(propriedadeArquivos.getTipoExtensaoArquivo()))
            throw new ServiceException("Nenhuma extensão de arquivo fora definida nas propriedades de execução do processamentos dos arquivos. Tipo, encontra-se inválido e/ou inexistente {NULL}.");
    }

    @Override
    public void validarContemArquivosObtidosFileDiretorioPadraoEntrada(List<File> fileInputDatList) throws RepositoryException {
        if (fileInputDatList.isEmpty())
            throw new RepositoryException("Não foram encontrados arquivos de leitura para entrada de dados. Pasta vazia.");
    }

    @Override
    public void validarFileEstahValidoParaObterLinhasRegistros(File file) throws ServiceException {
        if (ArquivoUtil.isFileInValido(file))
            throw new ServiceException("Arquivo encontra-se inválido e/ou inexistente {NULL}");
    }

    @Override
    public boolean isNotContainsArquivosDiretorioPadraoEntrada(File filePathInput) {
        return ArquivoUtil.isNotContainsArquivosFileDiretorio(filePathInput);
    }
}
