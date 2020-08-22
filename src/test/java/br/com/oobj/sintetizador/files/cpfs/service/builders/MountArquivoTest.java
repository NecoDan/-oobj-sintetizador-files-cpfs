package br.com.oobj.sintetizador.files.cpfs.service.builders;

import br.com.oobj.sintetizador.files.cpfs.ParametrosTestesUtil;
import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class MountArquivoTest {

    private MountArquivo mountArquivo;

    @Before
    public void setUp() {
        this.mountArquivo = new MountArquivo();
    }

    @Test
    public void deveMontarERetornarArquivoFromLinhaRegistroEPropriedadeArquivo() throws IOException {
        System.out.println("#TEST: deveMontarERetornarArquivoFromLinhaRegistroEPropriedadeArquivo: ");

        // -- 01_Cenário
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        File file = ParametrosTestesUtil.getUniqueFileFromCpf(tipoExtensaoArquivo);

        Arquivo arquivoSave = ParametrosTestesUtil.getUnicoArquivo(tipoExtensaoArquivo, file);
        String conteudoArquivo = arquivoSave.getConteudo();
        String nomeArquivo = arquivoSave.getCaminhoCompleto();
        ArquivoUtil.gravarArquivo(conteudoArquivo, new File(nomeArquivo));

        System.out.println("Parametros Cenarios: Arquivo {" + file.getAbsolutePath() + "}"
                + " | Extensao_Arquivo: " + "{" + tipoExtensaoArquivo.getCodigoLiteral().toUpperCase() + "}");

        PropriedadeArquivos propriedadeArquivos = PropriedadeArquivos.builder()
                .pathEntrada(file.getParentFile())
                .pathDestinoPadrao(file.getParentFile())
                // .tipoExtensaoArquivo(tipoExtensaoArquivo)
                .build();

        List<String> linhaRegistroArquivo = ArquivoUtil.getLinhasRegistrosArquivos(file);

        // -- 02_Ação
        List<Arquivo> arquivoList = linhaRegistroArquivo.stream()
                .filter(Objects::nonNull)
                .filter(linhaRegistro -> !linhaRegistro.isEmpty())
                .map(linhaRegistro -> this.mountArquivo.mountArquivoFromFile(linhaRegistro, propriedadeArquivos))
                .collect(Collectors.toList());

        // -- 03_Verificação_Validação
        assertFalse(arquivoList.isEmpty());

        for (Arquivo arquivo : arquivoList)
            System.out.println("Obj Arquivo Result: " + arquivo.getCaminhoCompleto());

        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveMontarERetornarArquivoFromFileEUmaTipoExtensaoArquivo() {
        System.out.println("#TEST: deveMontarERetornarArquivoFromFileEUmaTipoExtensaoArquivo: ");

        // -- 01_Cenário
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        File file = ParametrosTestesUtil.getUniqueFileFromCpf(tipoExtensaoArquivo);
        System.out.println("Parametros Cenarios: Arquivo {" + file.getAbsolutePath() + "}"
                + " | Extensao_Arquivo: " + "{" + tipoExtensaoArquivo.getCodigoLiteral().toUpperCase() + "}");

        // -- 02_Ação
        Arquivo arquivo = this.mountArquivo.mountArquivoFromFile(file, tipoExtensaoArquivo);

        // -- 03_Verificação_Validação
        assertNotNull(arquivo);
        System.out.println("Obj Arquivo Result: " + arquivo.getCaminhoCompleto());
        System.out.println("-------------------------------------------------------------");
    }
}
