package br.com.oobj.sintetizador.files.cpfs;

import br.com.oobj.sintetizador.files.cpfs.model.Arquivo;
import br.com.oobj.sintetizador.files.cpfs.model.TipoExtensaoArquivo;
import br.com.oobj.sintetizador.files.cpfs.service.builders.IMountArquivo;
import br.com.oobj.sintetizador.files.cpfs.service.builders.MountArquivo;
import br.com.oobj.sintetizador.files.cpfs.utils.GeraCpfUtil;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ParametrosTestesUtil {

    private final static int QTDE_ELEMENTOS_CFPS_A_GERA_RANDOMICAMENTE = 25;

    private ParametrosTestesUtil() {
    }

    public static String getDiretorioTempSistema() {
        String property = "java.io.tmpdir";
        String tempDir = System.getProperty(property);
        System.out.println("Diretorio temporario: " + tempDir);
        return tempDir;
    }

    public static File getFileDiretorioPadraoSistema() {
        return new File(getDiretorioTempSistema());
    }

    public static List<String> getListaCpfs() {
        List<String> cpfsList = new ArrayList<>();

        for (int i = 0; i <= QTDE_ELEMENTOS_CFPS_A_GERA_RANDOMICAMENTE; i++) {
            String cpf = GeraCpfUtil.cpf(false);
            cpfsList.add(cpf);
        }

        return cpfsList;
    }

    public static File getUniqueFileFromCpf(TipoExtensaoArquivo tipoExtensaoArquivo) {
        String cpf = GeraCpfUtil.cpf(false);
        String diretorioTemporarioSistema = getDiretorioTempSistema();
        return new File(diretorioTemporarioSistema + "/" + cpf + "." + tipoExtensaoArquivo.getCodigoLiteral());
    }

    public static Arquivo getUnicoArquivo(TipoExtensaoArquivo tipoExtensaoArquivo, File file) {
        if (Objects.isNull(file))
            file = getUniqueFileFromCpf(tipoExtensaoArquivo);
        return mountArquivoFromFile(file, tipoExtensaoArquivo);
    }

    public static List<Arquivo> getListArquivos() {
        List<Arquivo> arquivoList = new ArrayList<>();
        TipoExtensaoArquivo tipoExtensaoArquivo = TipoExtensaoArquivo.XML;
        String diretorioTemporarioSistema = getDiretorioTempSistema();

        for (String cpf : getListaCpfs()) {
            File file = new File(diretorioTemporarioSistema + "/" + cpf + "." + tipoExtensaoArquivo.getCodigoLiteral());
            arquivoList.add(mountArquivoFromFile(file, TipoExtensaoArquivo.XML));
        }

        return arquivoList;
    }

    public static Arquivo mountArquivoFromFile(File file, TipoExtensaoArquivo tipoExtensaoArquivo) {
        IMountArquivo mountArquivo = new MountArquivo();
        return mountArquivo.mountArquivoFromFile(file, tipoExtensaoArquivo);
    }

    public static String toStringLocalDateFormatadaPor(LocalDateTime data, String strFormato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(strFormato);
        return data.format(formatter);
    }
}
