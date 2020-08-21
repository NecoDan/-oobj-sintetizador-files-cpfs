package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.CPFUtil;
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

    final IValidadorProcessamentoService validadorProcessamentoService;
    final IArquivoService arquivoService;

    public GeradorArquivoService() {
        this.validadorProcessamentoService = new ValidadorProcessamentoService();
        this.arquivoService = new ArquivoService();
    }

    @Override
    public void processar(PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        this.validadorProcessamentoService.validarPropriedadesArquivo(propriedadeArquivos);
        List<File> fileList = this.arquivoService.recuperarArquivosFromPathEntrada(propriedadeArquivos);
        // processarFromMultiplasListas(propriedadeArquivos, fileList);
        processarFromCollections(propriedadeArquivos, fileList);
    }

    private void processarFromCollections(PropriedadeArquivos propriedadeArquivos, List<File> fileList) throws ServiceException {
        List<Arquivo> arquivosListFromFiles = new ArrayList<>();

        fileList.parallelStream().forEach(
                file -> {
                    try {
                        arquivosListFromFiles.addAll(obterListaArquivosAProcessarFrom(file, propriedadeArquivos));
                    } catch (ServiceException e) {
                        throw new RuntimeException(e.getLocalizedMessage());
                    }
                }
        );

        this.arquivoService.efetuarProcessamentosArquivos(arquivosListFromFiles, propriedadeArquivos);
    }

    private void processarFromMultiplasListas(PropriedadeArquivos propriedadeArquivos, List<File> fileList) throws ServiceException {
        List<Arquivo> arquivosListFromFiles = new ArrayList<>();
        List<Arquivo> arquivoListUnique = new ArrayList<>();
        List<Arquivo> arquivosListDuplicates = new ArrayList<>();

        fileList.parallelStream().forEach(
                file -> {
                    try {
                        for (Arquivo arquivo : obterListaArquivosAProcessarFrom(file, propriedadeArquivos)) {
                            if (arquivosListFromFiles.contains(arquivo)) {
                                arquivo.setCaminho(propriedadeArquivos.getPathDestinoArquivoAMover().getAbsolutePath());
                                arquivosListDuplicates.add(arquivo);
                                arquivoListUnique.removeIf(arq -> arq.getConteudo().equals(arquivo.getConteudo()));
                            } else {
                                arquivosListFromFiles.add(arquivo);
                                arquivoListUnique.add(arquivo);
                            }
                        }
                    } catch (ServiceException e) {
                        throw new RuntimeException(e.getLocalizedMessage());
                    }
                }
        );

        this.arquivoService.efetuarProcessamentosArquivos(arquivosListDuplicates, propriedadeArquivos);
        this.arquivoService.efetuarProcessamentosArquivos(arquivoListUnique, propriedadeArquivos);
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
                    .map(linhaRegistro -> mountArquivoFromFile(linhaRegistro, propriedadeArquivos))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    private Arquivo mountArquivoFromFile(String linhaRegistro, PropriedadeArquivos propriedadeArquivos) {
        return Arquivo.builder()
                .caminho(propriedadeArquivos.getPathDestinoPadrao().getAbsolutePath())
                .tipoExtensaoArquivo(propriedadeArquivos.getTipoExtensaoArquivo())
                .nomeArquivo(linhaRegistro)
                .conteudo(linhaRegistro)
                .build();
    }

    private boolean isLinhaRegistroValida(String linhaRegistro) {
        return (Objects.nonNull(linhaRegistro) && !linhaRegistro.isEmpty());
    }

    private boolean isConteudoContemNumeroCpfValido(String conteudoLinhaRegistro) {
        String numeroCpf = conteudoLinhaRegistro.replace(".", "");
        return CPFUtil.isCPFValido(numeroCpf);
    }

    private boolean isNaoContemArquivosAProcessar(List<Arquivo> arquivoList) {
        return (Objects.isNull(arquivoList) || arquivoList.isEmpty());
    }
}
