package br.com.oobj.sintetizador.files.cpfs.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RandomicoUtilTest {

    @Before
    public void setUp() {
    }

    @Test
    public void deveGerarValorRandomicoInteiro() {
        System.out.println("#TEST: deveGerarValorRandomicoInteiro: ");

        // -- 01_Cenário && -- 02_Ação
        int valor = RandomicoUtil.gerarValorRandomico();

        // -- 03_Verificacao_Validacao
        assertTrue(valor > 0);
        System.out.println("Valor resultado: ".concat(String.valueOf(valor)));
        System.out.println("-------------------------------------------------------------");
    }

    @Test
    public void deveGerarValorRandomicoLong() {
        System.out.println("#TEST: deveGerarValorRandomicoLong: ");

        // -- 01_Cenário && -- 02_Ação
        Long valor = RandomicoUtil.gerarValorRandomicoLong();

        // -- 03_Verificacao_Validacao
        assertTrue(valor > 0);
        System.out.println("Valor resultado: ".concat(String.valueOf(valor)));
        System.out.println("-------------------------------------------------------------");
    }
}
