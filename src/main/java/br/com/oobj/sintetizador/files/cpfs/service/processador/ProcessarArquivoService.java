package br.com.oobj.sintetizador.files.cpfs.service.processador;

import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import br.com.oobj.sintetizador.files.cpfs.service.geradores.GeradorArquivoService;
import br.com.oobj.sintetizador.files.cpfs.service.geradores.IGeradorArquivoService;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

public final class ProcessarArquivoService {

    private ProcessarArquivoService() {

    }

    public static void efetuarProcessamentoPor(String pathEntrada,
                                               String pathDestino,
                                               String pathDestinoArquivoAMover,
                                               TipoExtensaoArquivo tipoExtensaoArquivo) throws ServiceException {

        IGeradorArquivoService geradorArquivoService = new GeradorArquivoService();
        PropriedadeArquivos propriedadeArquivos = mountPropriedadeArquivosFrom(new File(pathEntrada), new File(pathDestino), new File(pathDestinoArquivoAMover), tipoExtensaoArquivo);
        geradorArquivoService.processar(propriedadeArquivos);
    }

    public static void efetuarProcessamento(File pathEntrada,
                                            File pathDestino,
                                            File pathDestinoArquivoAMover,
                                            TipoExtensaoArquivo tipoExtensaoArquivo) throws ServiceException {

        IGeradorArquivoService geradorArquivoService = new GeradorArquivoService();
        PropriedadeArquivos propriedadeArquivos = mountPropriedadeArquivosFrom(pathEntrada, pathDestino, pathDestinoArquivoAMover, tipoExtensaoArquivo);
        geradorArquivoService.processar(propriedadeArquivos);
    }

    public static void efetuarProcessamentosPor(Path pathEntrada,
                                                Path pathDestino,
                                                Path pathDestinoArquivoAMover,
                                                TipoExtensaoArquivo tipoExtensaoArquivo) throws ServiceException {

        if (isPathsParametrosInvalidos(pathEntrada, pathDestino, pathDestinoArquivoAMover))
            throw new ServiceException("Não existem parametros de diretórios e/ou pastas definidos para busca e gravação dos arquivos a serem processados.");

        IGeradorArquivoService geradorArquivoService = new GeradorArquivoService();
        PropriedadeArquivos propriedadeArquivos = mountPropriedadeArquivosFrom(pathEntrada.toFile(), pathDestino.toFile(), pathDestinoArquivoAMover.toFile(), tipoExtensaoArquivo);
        geradorArquivoService.processar(propriedadeArquivos);
    }

    private static boolean isPathsParametrosInvalidos(Path pathEntrada, Path pathDestino, Path pathDestinoArquivoAMover) {
        return (Objects.isNull(pathEntrada) || Objects.isNull(pathDestino) || Objects.isNull(pathDestinoArquivoAMover));
    }

    private static PropriedadeArquivos mountPropriedadeArquivosFrom(File pathEntrada,
                                                                    File pathDestino,
                                                                    File pathDestinoArquivoAMover,
                                                                    TipoExtensaoArquivo tipoExtensaoArquivo) {

        return PropriedadeArquivos.builder()
                .pathEntrada(pathEntrada)
                .pathDestinoPadrao(pathDestino)
                .pathDestinoArquivoAMover(pathDestinoArquivoAMover)
                .tipoExtensaoArquivo(Objects.isNull(tipoExtensaoArquivo) ? TipoExtensaoArquivo.XML : tipoExtensaoArquivo)
                .build();
    }

}
