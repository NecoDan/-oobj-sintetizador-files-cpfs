package br.com.oobj.sintetizador.files.cpfs.model;

import br.com.oobj.sintetizador.files.cpfs.utils.ArquivoUtil;
import br.com.oobj.sintetizador.files.cpfs.utils.RandomicoUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Arquivo {

    private String caminho;
    private String conteudo;
    private String nomeArquivo;
    private TipoExtensaoArquivo tipoExtensaoArquivo;

    public void gerarNomeArquivo(File file) {
        if (isParametersInvalidosAoEfetuarGeracao(file) || !this.nomeArquivo.isEmpty())
            return;
        this.nomeArquivo = file.getName().replace("." + this.tipoExtensaoArquivo.getCodigoLiteral(), "");
        this.nomeArquivo = this.nomeArquivo + "." + this.tipoExtensaoArquivo.getCodigoLiteral();
    }

    public void gerarConteudoArquivo(File file) {
        if (isParametersInvalidosAoEfetuarGeracao(file) || !this.conteudo.isEmpty())
            return;

        gerarNomeArquivo(file);
        this.conteudo = this.nomeArquivo;
    }

    public Arquivo geraNomeArquivo(File file) {
        gerarNomeArquivo(file);
        return this;
    }

    public Arquivo geraConteudoArquivo(File file) {
        gerarConteudoArquivo(file);
        return this;
    }

    public String getNomeArquivoCompleto() {
        return (isParamsNomeArquivoCompletoInValidos()) ? getNomeArquivoCompletoRandomico() : getNomeArquivoCompletoDefault();
    }

    public String getCaminhoCompleto() {
        return this.caminho + "/" + getNomeArquivoCompleto();
    }

    public File getFileCaminho() {
        return (Objects.isNull(this.caminho) || this.caminho.isEmpty()) ? null : new File(this.caminho);
    }

    private String getNomeArquivoCompletoDefault() {
        return (this.nomeArquivo.concat(".").concat(this.tipoExtensaoArquivo.getCodigoLiteral()));
    }

    private String getNomeArquivoCompletoRandomico() {
        return (String.valueOf(RandomicoUtil.gerarValorRandomicoLong()).concat(".").concat(this.tipoExtensaoArquivo.getCodigoLiteral()));
    }

    private boolean isPathCaminhoInvalido(File file) {
        return (Objects.isNull(file) || ArquivoUtil.isFileDiretorioInValido(file));
    }

    private boolean isParametersInvalidosAoEfetuarGeracao(File file) {
        return (isPathCaminhoInvalido(file) || Objects.isNull(this.tipoExtensaoArquivo));
    }

    private boolean isParamsNomeArquivoCompletoInValidos() {
        return ((Objects.isNull(this.nomeArquivo) || this.nomeArquivo.isEmpty()) && isTipoExtensaoArquivoInValida());
    }

    private boolean isTipoExtensaoArquivoInValida() {
        return (Objects.isNull(this.tipoExtensaoArquivo));
    }

    private boolean isTipoExtensaoArquivoValida() {
        return (!isTipoExtensaoArquivoInValida());
    }
}
