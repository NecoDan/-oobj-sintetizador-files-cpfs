package br.com.oobj.sintetizador.files.cpfs.repository;

import br.com.oobj.sintetizador.files.cpfs.ParametrosTestesUtil;
import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class ArquivoRepositoryTest {

    private ArquivoRepository arquivoRepository;
    private String diretorioTemporarioSistema;

    @Before
    public void setUp() {
        this.arquivoRepository = new ArquivoRepository();
        this.diretorioTemporarioSistema = ParametrosTestesUtil.getDiretorioTempSistema();
    }

    @Test
    public void deveRecuperarTodosArquivosNumDiretorioEntradaEspecifico() throws RepositoryException, IOException {
        System.out.println("#TEST: deveRecuperarTodosArquivosNumDiretorioEntradaEspecifico: ");

        // -- 01_Cenário
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        List<String> cpfsList = ParametrosTestesUtil.getListaCpfs();

        for (String cpf : cpfsList) {
            String nomeArquivo = cpf + "." + tipoExtensaoArquivo.getCodigoLiteral();
            File file = new File(diretorioTemporarioSistema + "/" + nomeArquivo);
            ArquivoUtil.gravarArquivo(nomeArquivo, file);
        }

        String pathDiretorioEntrada = this.diretorioTemporarioSistema;
        PropriedadeArquivos propriedadeArquivos = PropriedadeArquivos.builder().pathEntrada(new File(pathDiretorioEntrada)).build();

        // -- 02_Ação
        List<File> fileList = this.arquivoRepository.findAllFiles(propriedadeArquivos);

        // -- 03_Verificação_Validação
        assertTrue(fileList.size() > 0);
        System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveSalvarUmUnicoArquivo() throws RepositoryException {
        System.out.println("#TEST: deveSalvarUmUnicoArquivo: ");

        // -- 01_Cenário
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        File file = ParametrosTestesUtil.getUniqueFileFromCpf(tipoExtensaoArquivo);
        Arquivo arquivo = ParametrosTestesUtil.getUnicoArquivo(tipoExtensaoArquivo, file);

        // -- 02_Ação
        this.arquivoRepository.saveArquivo(arquivo);

        // -- 03_Verificação_Validação
        List<File> fileList = ArquivoUtil.buscarListaFiles(new File(this.diretorioTemporarioSistema), tipoExtensaoArquivo.getCodigoLiteral());

        Optional<File> first = fileList
                .stream()
                .filter(Objects::nonNull)
                .filter(f -> f.exists() && f.getName().contentEquals(file.getName()))
                .findFirst();

        assertTrue(first.isPresent());
        System.out.println("-------------------------------------------------------------");
    }
}
