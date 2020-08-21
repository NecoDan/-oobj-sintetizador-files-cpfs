package br.com.oobj.sintetizador.files.cpfs.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum TipoExtensaoArquivo {

    XML(1, "XML", "TIPO EXTENSAO ARQUIVO XML"),

    PDF(2, "PDF", "TIPO EXTENSAO ARQUIVO PDF"),

    TXT(3, "TXT", "TIPO EXTENSAO ARQUIVO TXT");

    private static final Map<Integer, TipoExtensaoArquivo> lookup;

    static {
        lookup = new HashMap<>();
        EnumSet<TipoExtensaoArquivo> enumSet = EnumSet.allOf(TipoExtensaoArquivo.class);

        for (TipoExtensaoArquivo type : enumSet)
            lookup.put(type.codigo, type);
    }

    private int codigo;
    private String codigoLiteral;
    private String descricao;

    TipoExtensaoArquivo(int codigo, String codigoLiteral, String descricao) {
        inicialize(codigo, codigoLiteral, descricao);
    }

    private void inicialize(int codigo, String codigoLiteral, String descricao) {
        this.codigo = codigo;
        this.codigoLiteral = codigoLiteral;
        this.descricao = descricao;
    }

    public static TipoExtensaoArquivo fromCodigo(int codigo) {
        if (lookup.containsKey(codigo))
            return lookup.get(codigo);
        throw new IllegalArgumentException(String.format("Código do Layout Arquivo inválido: %d", codigo));
    }

    public int getCodigo() {
        return this.codigo;
    }

    public String getCodigoLiteral() {
        return this.codigoLiteral.toLowerCase();
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean isXML() {
        return Objects.equals(this, XML);
    }

    public boolean isPDF() {
        return Objects.equals(this, PDF);
    }

    public boolean isTXT() {
        return Objects.equals(this, TXT);
    }
}
