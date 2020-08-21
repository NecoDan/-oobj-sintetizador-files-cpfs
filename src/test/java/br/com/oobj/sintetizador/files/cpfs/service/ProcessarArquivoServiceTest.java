package br.com.oobj.sintetizador.files.cpfs.service;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ProcessarArquivoServiceTest {

    @Test
    public void contextLoads() {
        List<String> input = FileUtils.listFiles(new File("/tmp/test/input"), FileFilterUtils.fileFileFilter(), null)
                .stream().map(File::getName).collect(Collectors.toList());

        List<String> output = FileUtils.listFiles(new File("/tmp/test/output"), FileFilterUtils.fileFileFilter(), null)
                .stream().map(File::getName).collect(Collectors.toList());

        Collection<String> subtract = CollectionUtils.intersection(input, output);

        subtract.forEach(f -> {
            try {
                FileUtils.copyFile(new File("/tmp/test/input/" + f), new File("/tmp/test/inout/" + f));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Before
    void setUp() {
    }

    @Test
    void efetuarProcessamentoPor() {
    }

    @Test
    void efetuarProcessamento() {

    }

    @Test
    void efetuarProcessamentosPor() {
    }
}
