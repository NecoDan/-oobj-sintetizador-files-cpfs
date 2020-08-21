package br.com.oobj.sintetizador.files.cpfs.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ArquivoUtil {

    private static final String PATH_DEFAULT_SAIDA = "/data/out";

    private ArquivoUtil() {
    }

    public static boolean isNotContainsArquivosFileDiretorio(File file) {
        return (Objects.isNull(file.listFiles()) || (Objects.nonNull(file.listFiles()) && Objects.requireNonNull(file.listFiles()).length == 0));
    }

    public static boolean isFileDiretorioInValido(File file) {
        return (!file.exists() || !file.isDirectory() || !file.canRead());
    }

    public static boolean isFileDiretorioValido(File file) {
        return (!isFileDiretorioInValido(file));
    }

    public static boolean isFileInValido(File file) {
        return (!isFileValido(file));
    }

    public static boolean isFileValido(File file) {
        return (file.exists() && file.canRead());
    }

    public static boolean isNameArquivoContainsNumeroCpf(File file, String extensaoArquivo) {
        return (!isParametersValidarNameArquivoContainsNumeroCpfInvalido(file, extensaoArquivo)) && isValidarNameArquivoContainsNumeroCpf(file, extensaoArquivo);
    }

    private static boolean isParametersValidarNameArquivoContainsNumeroCpfInvalido(File file, String extensaoArquivo) {
        return (isFileInValido(file) || Objects.isNull(extensaoArquivo) || extensaoArquivo.isEmpty());
    }

    private static boolean isValidarNameArquivoContainsNumeroCpf(File file, String extensaoArquivo) {
        String numeroCpf = file.getName().replace("." + extensaoArquivo, "");
        return CPFUtil.isCPFValido(numeroCpf);
    }

    public static List<File> buscarListaFilesNovo(File filePath, String extensaoArquivoFiltro) {
        return (List<File>) ((isNotContainsExtensaoComoFiltroBusca(extensaoArquivoFiltro))
                ? FileUtils.listFiles(filePath, FileFilterUtils.fileFileFilter(), null)
                : FileUtils.listFiles(filePath, FileFilterUtils.suffixFileFilter(extensaoArquivoFiltro), null));
    }

    private static boolean isNotContainsExtensaoComoFiltroBusca(String extensaoArquivoFiltro) {
        return (Objects.isNull(extensaoArquivoFiltro) || extensaoArquivoFiltro.isEmpty());
    }

    public static List<String> getLinhasRegistrosArquivos(File file) throws IOException {
        return (isFileInValido(file)) ? Collections.emptyList() : getLinhasRegistrosArquivosValidaFrom(file);
    }

    private static List<String> getLinhasRegistrosArquivosValidaFrom(File file) throws IOException {
        List<String> linhasArquivo;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
            linhasArquivo = new ArrayList<>();

            String linha = "";
            while (Objects.nonNull(linha)) {
                linha = bufferedReader.readLine();
                linhasArquivo.add(linha);
            }
        }
        return linhasArquivo;
    }

    public static void gravarArquivo(String conteudoArquivo, File arquivoNovo) throws IOException {
        try (FileWriter fileWriter = new FileWriter(arquivoNovo.getAbsolutePath())) {
            fileWriter.write(conteudoArquivo);
            fileWriter.flush();
        }
    }
    
    public static File gerarDiretorioPadraoArquivosSaidaAPartirSistema() {
        File filePathDiretorioSaidaSistema = new File(System.getProperty("user.home") + PATH_DEFAULT_SAIDA);
        return (filePathDiretorioSaidaSistema.exists()) ? filePathDiretorioSaidaSistema : criarPathDiretorioInexistente(filePathDiretorioSaidaSistema);
    }

    private static File criarPathDiretorioInexistente(File filePathDiretorio) {
        boolean created = filePathDiretorio.mkdir();
        return (created) ? filePathDiretorio : null;
    }
}
