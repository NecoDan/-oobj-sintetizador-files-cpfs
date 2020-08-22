package br.com.oobj.sintetizador.files.cpfs.utils;

import br.com.oobj.sintetizador.files.cpfs.ParametrosTestesUtil;
import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArquivoUtilTest {

    @Before
    public void setUp() {

    }

    @Test
    public void isNotContainsArquivosFileDiretorio() {
        System.out.println("#TEST: deveRetornarUmaStringComADataFormatadaAPartirDataValida: ");

        // -- 01_Cenário
        File file = ParametrosTestesUtil.getFileDiretorioPadraoSistema();
        System.out.println("Diretorio File: ".concat(file.getAbsolutePath()));

        // -- 02_Ação && 03_Verificacao-Validacao
        assertFalse(ArquivoUtil.isNotContainsArquivosFileDiretorio(file));
        System.out.println("Qtde Arquivos Diretorio: ".concat(String.valueOf(Objects.requireNonNull(file.listFiles()).length)));
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void isFileDiretorioInValido() {
        System.out.println("#TEST: isFileDiretorioInValido: ");

        // -- 01_Cenário
        File file = null;

        // -- 02_Ação && 03_Verificacao-Validacao
        assertTrue(ArquivoUtil.isFileDiretorioInValido(file));
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void isFileDiretorioValido() {
        System.out.println("#TEST: isFileDiretorioValido: ");

        // -- 01_Cenário
        File file = ParametrosTestesUtil.getFileDiretorioPadraoSistema();

        // -- 02_Ação && 03_Verificacao-Validacao
        assertTrue(ArquivoUtil.isFileDiretorioValido(file));
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void isFileInValido() {
        System.out.println("#TEST: isFileInValido: ");

        // -- 01_Cenário
        File file = ParametrosTestesUtil.getFileDiretorioPadraoSistema();

        // -- 02_Ação && 03_Verificacao-Validacao
        assertTrue(ArquivoUtil.isFileValido(file));
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveBuscarListaFilesPorExtensaoFiltro() throws IOException {
        System.out.println("#TEST: deveBuscarListaFilesPorExtensaoFiltro: ");

        // -- 01_Cenário
        File fileDiretorioTemporarioSistema = ParametrosTestesUtil.getFileDiretorioPadraoSistema();
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        List<String> cpfsList = ParametrosTestesUtil.getListaCpfs();

        for (String cpf : cpfsList) {
            String nomeArquivo = cpf + "." + tipoExtensaoArquivo.getCodigoLiteral();
            File file = new File(fileDiretorioTemporarioSistema.getAbsolutePath() + "/" + nomeArquivo);
            ArquivoUtil.gravarArquivo(nomeArquivo, file);
        }

        // -- 02_Ação
        List<File> fileList = ArquivoUtil.buscarListaFiles(fileDiretorioTemporarioSistema, tipoExtensaoArquivo.getCodigoLiteral());

        // -- 03_Verificação_Validação
        assertTrue(fileList.size() > 0);
        System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveBuscarListaFilesPorQualquerExtensaoFiltro() throws IOException {
        System.out.println("#TEST: deveBuscarListaFilesPorQualquerExtensaoFiltro: ");

        // -- 01_Cenário
        File fileDiretorioTemporarioSistema = ParametrosTestesUtil.getFileDiretorioPadraoSistema();

        TipoExtensaoArquivo tipoExtensaoArquivoTxt = TipoExtensaoArquivo.TXT;
        TipoExtensaoArquivo tipoExtensaoArquivoXML = TipoExtensaoArquivo.XML;
        List<String> cpfsList = ParametrosTestesUtil.getListaCpfs();

        for (String cpf : cpfsList) {
            String nomeArquivoXML = cpf + "." + tipoExtensaoArquivoXML.getCodigoLiteral();
            File fileXml = new File(fileDiretorioTemporarioSistema.getAbsolutePath() + "/" + nomeArquivoXML);
            ArquivoUtil.gravarArquivo(nomeArquivoXML, fileXml);

            String nomeArquivoTxt = cpf + "." + tipoExtensaoArquivoTxt.getCodigoLiteral();
            File fileTxt = new File(fileDiretorioTemporarioSistema.getAbsolutePath() + "/" + nomeArquivoTxt);
            ArquivoUtil.gravarArquivo(nomeArquivoTxt, fileTxt);
        }

        // -- 02_Ação
        List<File> fileList = ArquivoUtil.buscarListaFiles(fileDiretorioTemporarioSistema, null);

        // -- 03_Verificação_Validação
        assertTrue(fileList.size() > 0);
        System.out.println("Contains arquivos a serem lidos no diretorio: " + fileList.size());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveObterUmaListaComAsLinhasRegistrosArquivos() throws IOException {
        System.out.println("#TEST: deveObterUmaListaComAsLinhasRegistrosArquivos: ");

        // -- 01_Cenário
        File fileDiretorioTemporarioSistema = ParametrosTestesUtil.getFileDiretorioPadraoSistema();
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.TXT;
        String nomeArquivo = String.valueOf(RandomicoUtil.gerarValorRandomicoLong()).concat("_")
                .concat(ParametrosTestesUtil.toStringLocalDateFormatadaPor(LocalDateTime.now(), "dd-MM-yyyy_hh-mm-ss"));

        List<String> cpfsList = ParametrosTestesUtil.getListaCpfs();
        StringBuilder stringBuilder = new StringBuilder();
        for (String cpf : cpfsList) {
            stringBuilder.append(cpf).append(".").append(tipoExtensaoArquivo.getCodigoLiteral()).append("\n");
        }

        String conteudo = stringBuilder.toString();
        File file = new File(fileDiretorioTemporarioSistema.getAbsolutePath() + "/" + nomeArquivo + tipoExtensaoArquivo.getCodigoLiteral());
        ArquivoUtil.gravarArquivo(conteudo, file);

        // -- 02_Ação
        List<String> linhasRegistrosArquivo = ArquivoUtil.getLinhasRegistrosArquivos(file);

        // -- 03_Verificação_Validação
        assertTrue((Objects.nonNull(linhasRegistrosArquivo)) && !linhasRegistrosArquivo.isEmpty());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveGravarArquivo() throws IOException {
        System.out.println("#TEST: deveGravarArquivo: ");

        // -- 01_Cenário
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        File file = ParametrosTestesUtil.getUniqueFileFromCpf(tipoExtensaoArquivo);
        Arquivo arquivo = ParametrosTestesUtil.getUnicoArquivo(tipoExtensaoArquivo, file);

        // -- 02_Ação
        File fileDiretorioTemporarioSistema = ParametrosTestesUtil.getFileDiretorioPadraoSistema();
        String conteudoArquivo = arquivo.getConteudo();
        String nomeArquivo = arquivo.getCaminhoCompleto();
        ArquivoUtil.gravarArquivo(conteudoArquivo, new File(nomeArquivo));

        // -- 03_Verificação_Validação
        List<File> fileList = ArquivoUtil.buscarListaFiles(fileDiretorioTemporarioSistema, tipoExtensaoArquivo.getCodigoLiteral());

        Optional<File> first = fileList
                .stream()
                .filter(Objects::nonNull)
                .filter(f -> f.exists() && f.getName().contentEquals(file.getName()))
                .findFirst();

        assertTrue(first.isPresent());
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveGerarDiretorioPadraoArquivosSaidaAPartirSistema() {
        System.out.println("#TEST: gerarDiretorioPadraoArquivosSaidaAPartirSistema: ");

        // -- 01_Cenário
        File file = ArquivoUtil.gerarDiretorioPadraoArquivosSaidaAPartirSistema();

        // -- 02_Ação && 03_Verificacao-Validacao
        assertTrue(Objects.nonNull(file) && ArquivoUtil.isFileDiretorioValido(file));
        System.out.println("-------------------------------------------------------------");
    }


}
