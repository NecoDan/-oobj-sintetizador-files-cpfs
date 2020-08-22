package br.com.oobj.sintetizador.files.cpfs.repository;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import br.com.oobj.sintetizador.files.cpfs.validation.IValidadorProcessamentoService;
import br.com.oobj.sintetizador.files.cpfs.validation.ValidadorProcessamentoService;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil.*;

public class ArquivoRepository implements IArquivoRepository {

    private final IValidadorProcessamentoService validadorProcessamentoService;

    public ArquivoRepository() {
        this.validadorProcessamentoService = new ValidadorProcessamentoService();
    }

    @Override
    public List<File> findAllFiles(PropriedadeArquivos propriedadeArquivos) throws RepositoryException {
        List<File> fileInputDatList = procurar(propriedadeArquivos.getPathEntrada(), null);
        this.validadorProcessamentoService.validarContemArquivosObtidosFileDiretorioPadraoEntrada(fileInputDatList);
        return fileInputDatList;
    }

    private List<File> procurar(File filePathInputDefault, String extensaoArquivoBusca) {
        return (validadorProcessamentoService.isNotContainsArquivosDiretorioPadraoEntrada(filePathInputDefault))
                ? Collections.emptyList()
                : buscarListaFiles(filePathInputDefault, extensaoArquivoBusca);
    }

    @Override
    public void saveArquivo(Arquivo arquivo) throws RepositoryException {
        String conteudoArquivo = arquivo.getConteudo();
        File arquivoNovo = mountFileFrom(arquivo);
        salvar(conteudoArquivo, arquivoNovo);
    }

    @Override
    public File mountFileFrom(Arquivo arquivo) {
        String nomeArquivo = arquivo.getCaminhoCompleto();
        File filePathOuPutSave = arquivo.getFileCaminho();
        return getFileArquioNovoSave(filePathOuPutSave, nomeArquivo);
    }

    private File getFileArquioNovoSave(File filePathOuPutSave, String nomeArquivo) {
        return (isFileDiretorioInValido(filePathOuPutSave)) ? createPathPadraoComArquivo(nomeArquivo) : new File(nomeArquivo);
    }

    private File createPathPadraoComArquivo(String nomeArquivo) {
        File filePathOuPutSave = gerarDiretorioPadraoArquivosSaidaAPartirSistema();
        return new File(filePathOuPutSave.getAbsolutePath().concat("/").concat(nomeArquivo));
    }

    @Override
    public void salvar(String conteudoArquivo, File arquivoNovo) throws RepositoryException {
        try {
            gravarArquivo(conteudoArquivo, arquivoNovo);
        } catch (IOException e) {
            throw new RepositoryException("Houve erro ao criar o arquivo de saida {output} file CPF.xml");
        } finally {
            System.out.println("Arquivo criado com sucesso: " + arquivoNovo.getAbsolutePath());
        }
    }
}
