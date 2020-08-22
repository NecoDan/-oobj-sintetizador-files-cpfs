package br.com.oobj.sintetizador.files.cpfs.service.builders;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;

import java.io.File;

public class MountArquivo implements IMountArquivo {

    @Override
    public Arquivo mountArquivoFromFile(String linhaRegistro, PropriedadeArquivos propriedadeArquivos) {
        return Arquivo.builder()
                .caminho(propriedadeArquivos.getPathDestinoPadrao().getAbsolutePath())
                .tipoExtensaoArquivo(propriedadeArquivos.getTipoExtensaoArquivo())
                .nomeArquivo(linhaRegistro)
                .conteudo(linhaRegistro)
                .build();
    }

    @Override
    public Arquivo mountArquivoFromFile(File file, TipoExtensaoArquivo tipoExtensaoArquivo) {
        return Arquivo.builder()
                .caminho(file.getParent())
                .tipoExtensaoArquivo(tipoExtensaoArquivo)
                .build()
                .geraNomeArquivo(file)
                .geraConteudoArquivo(file);
    }
}
