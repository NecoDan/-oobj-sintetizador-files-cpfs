package br.com.oobj.sintetizador.files.cpfs.service.builders;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;

import java.io.File;

public interface IMountArquivo {

    Arquivo mountArquivoFromFile(String linhaRegistro, PropriedadeArquivos propriedadeArquivos);

    Arquivo mountArquivoFromFile(File file, TipoExtensaoArquivo tipoExtensaoArquivo);
}
