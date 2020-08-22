package br.com.oobj.sintetizador.files.cpfs.service.geradores;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.service.ArquivoService;
import br.com.oobj.sintetizador.files.cpfs.service.IArquivoService;
import br.com.oobj.sintetizador.files.cpfs.service.builders.IMountArquivo;
import br.com.oobj.sintetizador.files.cpfs.service.builders.MountArquivo;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.CpfUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;
import br.com.oobj.sintetizador.files.cpfs.validation.IValidadorProcessamentoService;
import br.com.oobj.sintetizador.files.cpfs.validation.ValidadorProcessamentoService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GeradorArquivoService implements IGeradorArquivoService {

    final IMountArquivo mountArquivo;
    final IValidadorProcessamentoService validadorProcessamentoService;
    final IArquivoService arquivoService;

    public GeradorArquivoService() {
        this.mountArquivo = new MountArquivo();
        this.validadorProcessamentoService = new ValidadorProcessamentoService();
        this.arquivoService = new ArquivoService();
    }

    @Override
    public void processar(PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        this.validadorProcessamentoService.validarPropriedadesArquivo(propriedadeArquivos);
        List<File> fileList = this.arquivoService.recuperarArquivosFromPathEntrada(propriedadeArquivos);
        efetuarProcessamentos(propriedadeArquivos, fileList);
    }

    @Override
    public void efetuarProcessamentos(PropriedadeArquivos propriedadeArquivos, List<File> fileList) throws ServiceException {
        List<Arquivo> arquivosListFromFiles = new ArrayList<>();

        fileList.parallelStream().forEach(
                file -> {
                    try {
                        arquivosListFromFiles.addAll(obterListaArquivosAProcessarFrom(file, propriedadeArquivos));
                    } catch (ServiceException e) {
                        System.out.println(e.getLocalizedMessage());
                        throw new RuntimeException(e.getLocalizedMessage());
                    }
                }
        );

        this.arquivoService.efetuarProcessamentosArquivos(arquivosListFromFiles, propriedadeArquivos);
    }

    @Override
    public List<Arquivo> obterListaArquivosAProcessarFrom(File file, PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        this.validadorProcessamentoService.validarFileEstahValidoParaObterLinhasRegistros(file);
        this.validadorProcessamentoService.validarPropriedadesArquivo(propriedadeArquivos);

        try {
            return ArquivoUtil.getLinhasRegistrosArquivos(file)
                    .stream()
                    .filter(this::isLinhaRegistroValida)
                    .filter(this::isConteudoContemNumeroCpfValido)
                    .map(linhaRegistro -> this.mountArquivo.mountArquivoFromFile(linhaRegistro, propriedadeArquivos))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    private boolean isLinhaRegistroValida(String linhaRegistro) {
        return (Objects.nonNull(linhaRegistro) && !linhaRegistro.isEmpty());
    }

    private boolean isConteudoContemNumeroCpfValido(String conteudoLinhaRegistro) {
        String numeroCpf = conteudoLinhaRegistro.replace(".", "");
        return CpfUtil.isCPFValido(numeroCpf);
    }
}
