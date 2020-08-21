package br.com.oobj.sintetizador.files.cpfs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropriedadeArquivos {
    private File pathEntrada;
    private File pathDestinoPadrao;
    private File pathDestinoArquivoAMover;
    private TipoExtensaoArquivo tipoExtensaoArquivo;
}
