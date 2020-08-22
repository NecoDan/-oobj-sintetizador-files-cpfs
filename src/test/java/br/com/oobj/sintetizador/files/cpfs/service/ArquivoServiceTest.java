package br.com.oobj.sintetizador.files.cpfs.service;

import br.com.oobj.sintetizador.files.cpfs.model.PropriedadeArquivos;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArquivoServiceTest {

    private IArquivoService arquivoService;

    @Before
    public void setUp() throws Exception {
        this.arquivoService = new ArquivoService();
    }

    @Test
    public void deveLancarExceptionAoRecuperarArquivosFromPathEntrada() {
        System.out.println("#TEST: deveLancarExceptionAoRecuperarArquivosFromPathEntrada: ");

        // -- 01_Cenário && 02_Ação
        ServiceException exception = assertThrows(ServiceException.class,
                () -> this.arquivoService.recuperarArquivosFromPathEntrada(null));

        // -- 03_Verificação_Validação
        assertTrue(exception.getMessage().contains("As propriedades de execução"));
        System.out.println("EXCEPTION: ".concat(exception.getMessage()));
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveLancarExceptionAoEfetuarProcessamentosArquivos() {
        System.out.println("#TEST: deveLancarExceptionAoEfetuarProcessamentosArquivos: ");

        // -- 01_Cenário && 02_Ação
        ServiceException exception = assertThrows(ServiceException.class,
                () -> this.arquivoService.efetuarProcessamentosArquivos(null, PropriedadeArquivos.builder().build()));

        // -- 03_Verificação_Validação
        assertTrue(exception.getMessage().contains("existem arquivos"));
        System.out.println("EXCEPTION: ".concat(exception.getMessage()));
        System.out.println("-------------------------------------------------------------");
    }
}
