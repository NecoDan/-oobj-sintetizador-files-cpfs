package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public class ProcessarArquivoService implements IProcessarArquivoService {

    final IGeradorArquivoService geradorArquivoService;

    public ProcessarArquivoService() {
        this.geradorArquivoService = new GeradorArquivoService();
    }

    @Override
    public void efetuarProcessamentoPor(String pathEntrada, String pathDestino, String pathDestinoArquivoAMover) throws ServiceException {
        PropriedadeArquivos propriedadeArquivos = mountPropriedadeArquivosFrom(new File(pathEntrada), new File(pathDestino), new File(pathDestinoArquivoAMover));
        this.geradorArquivoService.processar(propriedadeArquivos);
    }

    @Override
    public void efetuarProcessamento(File pathEntrada, File pathDestino, File pathDestinoArquivoAMover) throws ServiceException {
        PropriedadeArquivos propriedadeArquivos = mountPropriedadeArquivosFrom(pathEntrada, pathDestino, pathDestinoArquivoAMover);
        this.geradorArquivoService.processar(propriedadeArquivos);
    }

    @Override
    public void efetuarProcessamentosPor(Path pathEntrada, Path pathDestino, Path pathDestinoArquivoAMover) throws ServiceException {
        if (isPathsParametrosInvalidos(pathEntrada, pathDestino, pathDestinoArquivoAMover))
            return;

        PropriedadeArquivos propriedadeArquivos = mountPropriedadeArquivosFrom(pathEntrada.toFile(), pathDestino.toFile(), pathDestinoArquivoAMover.toFile());
        this.geradorArquivoService.processar(propriedadeArquivos);
    }

    private boolean isPathsParametrosInvalidos(Path pathEntrada, Path pathDestino, Path pathDestinoArquivoAMover) {
        return (Objects.isNull(pathEntrada) || Objects.isNull(pathDestino) || Objects.isNull(pathDestinoArquivoAMover));
    }

    private PropriedadeArquivos mountPropriedadeArquivosFrom(File pathEntrada, File pathDestino, File pathDestinoArquivoAMover) {
        return PropriedadeArquivos.builder()
                .pathEntrada(pathEntrada)
                .pathDestinoPadrao(pathDestino)
                .pathDestinoArquivoAMover(pathDestinoArquivoAMover)
                .tipoExtensaoArquivo(TipoExtensaoArquivo.XML)
                .build();
    }
}
