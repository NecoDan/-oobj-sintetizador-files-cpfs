package br.com.oobj.sintetizador.files.cpfs.service.processador;


import br.com.oobj.sintetizador.files.cpfs.ParametrosTestesUtil;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.RandomicoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ProcessarArquivoServiceTest {

    private File fileDiretorioTemporarioSistema;

    private String pathEntrada;
    private String pathSaida;
    private String pathSaidaArquivosMovidos;

    private File filePathEntrada;
    private File filePathSaida;
    private File filePathSaidaArquivosMovidos;

    @Before
    public void setUp() throws IOException {
        this.fileDiretorioTemporarioSistema = ParametrosTestesUtil.getFileDiretorioPadraoSistema();

        this.pathEntrada = fileDiretorioTemporarioSistema.getAbsolutePath() + "/entrada";
        this.pathSaida = fileDiretorioTemporarioSistema.getAbsolutePath() + "/saida";
        this.pathSaidaArquivosMovidos = fileDiretorioTemporarioSistema.getAbsolutePath() + "/saidaAMover";

        this.filePathEntrada = ArquivoUtil.criarPathDiretorioInexistente(new File(this.pathEntrada));
        this.filePathSaida = ArquivoUtil.criarPathDiretorioInexistente(new File(this.pathSaida));
        this.filePathSaidaArquivosMovidos = ArquivoUtil.criarPathDiretorioInexistente(new File(this.pathSaidaArquivosMovidos));

        inicializarArquivos();
    }

    public void inicializarArquivos() throws IOException {
        TipoExtensaoArquivo txt = TipoExtensaoArquivo.TXT;
        String nomeArquivo = String.valueOf(RandomicoUtil.gerarValorRandomicoLong())
                .concat("_")
                .concat(ParametrosTestesUtil.toStringLocalDateFormatadaPor(LocalDateTime.now(), "dd-MM-yyyy_hh-mm-ss"));

        List<String> cpfsList = ParametrosTestesUtil.getListaCpfs();
        StringBuilder stringBuilder = new StringBuilder();
        for (String cpf : cpfsList) {
            stringBuilder.append(cpf).append("\n");
        }

        String conteudo = stringBuilder.toString();
        File file = new File(this.filePathEntrada.getAbsolutePath() + "/" + nomeArquivo.concat(".").concat(txt.getCodigoLiteral()));
        ArquivoUtil.gravarArquivo(conteudo, file);
    }

    @Test
    public void deveEfetuarProcessamentoArquivosPorParametrosPathEmString() throws IOException, ServiceException {
        System.out.println("#TEST: deveEfetuarProcessamentoArquivosPorParametrosPathEmString: ");

        // -- 01_Cenário
        // -- 02_Ação
        TipoExtensaoArquivo xml = TipoExtensaoArquivo.XML;
        ProcessarArquivoService.efetuarProcessamentoPor(pathEntrada, pathSaida, pathSaidaArquivosMovidos, xml);

        // -- 03_Verificação_Validação
        List<File> fileListGravados = ArquivoUtil.buscarListaFiles(new File(pathSaidaArquivosMovidos), xml.getCodigoLiteral());
        assertTrue(fileListGravados.size() > 0);

        System.out.println("Total arquivos gravados e lidos no diretorio {SAIDA_MOVIDOS}: " + fileListGravados.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveEfetuarProcessamentoArquivosPorParametrosPathEmFile() throws IOException, ServiceException {
        System.out.println("#TEST: deveEfetuarProcessamentoArquivosPorParametrosPathEmFile: ");

        // -- 01_Cenário
        // -- 02_Ação
        TipoExtensaoArquivo xml = TipoExtensaoArquivo.XML;
        ProcessarArquivoService.efetuarProcessamento(filePathEntrada, filePathSaida, filePathSaidaArquivosMovidos, xml);

        // -- 03_Verificação_Validação
        List<File> fileListGravados = ArquivoUtil.buscarListaFiles(new File(pathSaidaArquivosMovidos), xml.getCodigoLiteral());
        assertTrue(fileListGravados.size() > 0);

        System.out.println("Total arquivos gravados e lidos no diretorio {SAIDA_MOVIDOS}: " + fileListGravados.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveEfetuarProcessamentoArquivosPorParametrosPathEmPaths() throws IOException, ServiceException {
        System.out.println("#TEST: deveEfetuarProcessamentoArquivosPorParametrosPathEmPaths: ");

        // -- 01_Cenário
        Path objPathEntrada = Paths.get(this.pathEntrada);
        Path objPathSaida = Paths.get(this.pathSaida);
        Path objPathSaidaArquivosMover = Paths.get(this.pathSaidaArquivosMovidos);

        // -- 02_Ação
        TipoExtensaoArquivo xml = TipoExtensaoArquivo.XML;
        ProcessarArquivoService.efetuarProcessamentosPor(objPathEntrada, objPathSaida, objPathSaidaArquivosMover, xml);

        // -- 03_Verificação_Validação
        List<File> fileListGravados = ArquivoUtil.buscarListaFiles(new File(pathSaidaArquivosMovidos), xml.getCodigoLiteral());
        assertTrue(fileListGravados.size() > 0);

        System.out.println("Total arquivos gravados e lidos no diretorio {SAIDA_MOVIDOS}: " + fileListGravados.size());
        System.out.println("-------------------------------------------------------------");
    }
}
