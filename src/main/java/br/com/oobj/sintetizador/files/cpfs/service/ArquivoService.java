package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.repository.ArquivoRepository;
import br.com.oobj.sintetizador.files.cpfs.repository.IArquivoRepository;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;
import br.com.oobj.sintetizador.files.cpfs.validation.IValidadorProcessamentoService;
import br.com.oobj.sintetizador.files.cpfs.validation.ValidadorProcessamentoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArquivoService implements IArquivoService {

    private final IArquivoRepository arquivoRepository;
    private final IValidadorProcessamentoService validadorProcessamentoService;

    public ArquivoService() {
        this.arquivoRepository = new ArquivoRepository();
        this.validadorProcessamentoService = new ValidadorProcessamentoService();
    }

    @Override
    public List<File> recuperarArquivosFromPathEntrada(PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        try {
            return this.arquivoRepository.findAllFiles(propriedadeArquivos);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<Arquivo> recuperarArquivosFromPathDestino(PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        try {
            this.validadorProcessamentoService.validarPropriedadesArquivo(propriedadeArquivos);
            return this.arquivoRepository.findAllArquivosPathDestino(propriedadeArquivos);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void salvarEmLote(List<Arquivo> arquivoList) throws ServiceException {
        for (Arquivo arquivo : arquivoList)
            salvar(arquivo);
    }

    @Override
    public void salvar(final Arquivo arquivo) throws ServiceException {
        try {
            this.arquivoRepository.saveArquivo(arquivo);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void efetuarProcessamentosArquivos(List<Arquivo> arquivoList, PropriedadeArquivos propriedadeArquivos) throws ServiceException {
        if (arquivoList.isEmpty())
            throw new ServiceException("Não existem arquivos a serem processados.");

        String extensaoArquivoFilter = propriedadeArquivos.getTipoExtensaoArquivo().getCodigoLiteral();

        List<String> outputList = ArquivoUtil.buscarListaFilesNovo(propriedadeArquivos.getPathDestinoPadrao(), extensaoArquivoFilter)
                .stream()
                .map(File::getName)
                .collect(Collectors.toList());

        List<String> inputList = getListNamesInputFromListFiles(arquivoList);

        salvarListArquivos(arquivoList);
        moverListArquivosExistentensDiretorioPadraoSaida(propriedadeArquivos, inputList, outputList);
    }

    private void moverListArquivosExistentensDiretorioPadraoSaida(PropriedadeArquivos propriedadeArquivos, List<String> input, List<String> output) {
        Collection<String> strListIntersection = CollectionUtils.intersection(input, output);

        strListIntersection.forEach(strNomeArquivo -> {
            try {
                FileUtils.moveFile(
                        new File(propriedadeArquivos.getPathDestinoPadrao().getAbsolutePath().concat("/") + strNomeArquivo),
                        new File(propriedadeArquivos.getPathDestinoArquivoAMover().getAbsolutePath().concat("/") + strNomeArquivo)
                );
            } catch (IOException e) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
        });
    }

    private void salvarListArquivos(List<Arquivo> arquivoList) throws ServiceException {
        List<Arquivo> arquivosListUnica = getListaArquivosUnica(arquivoList);
        salvarEmLote(arquivosListUnica);
    }

    private List<String> getListNamesInputFromListFiles(List<Arquivo> arquivoList) {
        Collection<File> filesInput = arquivoList
                .stream()
                .filter(Objects::nonNull)
                .map(this.arquivoRepository::mountFileFrom)
                .collect(Collectors.toList());

        return filesInput.stream().map(File::getName).collect(Collectors.toList());
    }

    private List<Arquivo> getListaArquivosUnica(List<Arquivo> arquivoList) {
        return arquivoList
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Arquivo::getNomeArquivo))
                .distinct()
                .collect(Collectors.toList());
    }
}
