package br.com.oobj.sintetizador.files.cpfs.repository;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;

import java.io.File;
import java.util.List;

public interface IArquivoRepository {

    List<File> findAllFiles(PropriedadeArquivos propriedadeArquivos) throws RepositoryException;

    List<Arquivo> findAllArquivosPathDestino(PropriedadeArquivos propriedadeArquivos) throws RepositoryException;

    void saveArquivo(Arquivo arquivo) throws RepositoryException;

    File mountFileFrom(Arquivo arquivo);
}
