package br.com.oobj.sintetizador.files.cpfs.repository;

import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.RepositoryException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ArquivoRepositoryTest {

    private ArquivoRepository arquivoRepository;

    @Before
    public void setUp() {
        this.arquivoRepository = new ArquivoRepository();
    }

    @Test
    public void deveRecuperarTodosArquivosNumDiretorioEntradaEspecifico() throws RepositoryException {
        System.out.println("#TEST: deveRecuperarTodosArquivosNumDiretorioEntradaEspecifico:");

        // -- 01_Cenário
        String pathDiretorioEntrada = "/home/danielsantos/data/in";
        PropriedadeArquivos propriedadeArquivos = PropriedadeArquivos.builder().pathEntrada(new File(pathDiretorioEntrada)).build();

        // -- 02_Ação
        List<File> fileList = this.arquivoRepository.findAllFiles(propriedadeArquivos);

        // -- 03_Verificação_Validação
        assertTrue(fileList.size() > 0);
        System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void findAllArquivos() {
    }
}
