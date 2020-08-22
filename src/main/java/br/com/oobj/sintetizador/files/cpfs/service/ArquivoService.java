package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.repository.ArquivoRepository;
import br.com.oobj.sintetizador.files.cpfs.repository.IArquivoRepository;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;
import br.com.oobj.sintetizador.files.cpfs.validation.IValidadorProcessamentoService;
import br.com.oobj.sintetizador.files.cpfs.validation.ValidadorProcessamentoService;
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
            this.validadorProcessamentoService.validarPropriedadesArquivo(propriedadeArquivos);
            return this.arquivoRepository.findAllFiles(propriedadeArquivos);
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

        List<Arquivo> arquivosListUnica = getListaArquivosUnica(arquivoList);
        salvarEmLote(arquivosListUnica);

        List<String> inputList = getListNamesInputFromListFiles(arquivosListUnica);
        moverListArquivosExistentensDiretorioPadraoSaida(propriedadeArquivos, inputList);
    }

    private void moverListArquivosExistentensDiretorioPadraoSaida(PropriedadeArquivos propriedadeArquivos, List<String> inputList) {
        inputList.forEach(strNomeArquivo -> {
            try {
                moverArquivoFrom(strNomeArquivo, propriedadeArquivos);
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
                throw new RuntimeException(e.getLocalizedMessage());
            }
        });
    }

    private void moverArquivoFrom(String strNomeArquivo, PropriedadeArquivos propriedadeArquivos) throws IOException {
        File fileExists = new File(propriedadeArquivos.getPathDestinoPadrao().getAbsolutePath().concat("/") + strNomeArquivo);
        File fileMove = new File(propriedadeArquivos.getPathDestinoArquivoAMover().getAbsolutePath().concat("/") + strNomeArquivo);

        if (!fileMove.exists()) {
            FileUtils.moveFile(fileExists, fileMove);
            return;
        }

        FileUtils.forceDelete(fileMove);
        FileUtils.moveFile(fileExists, fileMove);
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
