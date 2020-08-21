package br.com.oobj.sintetizador.files.cpfs.app;

import br.com.oobj.sintetizador.files.cpfs.service.IProcessarArquivoService;
import br.com.oobj.sintetizador.files.cpfs.service.ProcessarArquivoService;
import br.com.oobj.sintetizador.files.cpfs.utils.exceptions.ServiceException;

public class App {

    private static final String PATH_ENTRADA = "/home/danielsantos/data/in";
    private static final String PATH_DESTINO = "/home/danielsantos/data/out";
    private static final String PATH_MOVER = "/home/danielsantos/data/move";

    public static void main(String[] args) {
        final IProcessarArquivoService processarArquivoService = new ProcessarArquivoService();
        try {
            processarArquivoService.efetuarProcessamentoPor(PATH_ENTRADA, PATH_DESTINO, PATH_MOVER);
        } catch (ServiceException e) {
            System.out.println("Houve falha na aplicação: " + e.getLocalizedMessage());
        }
    }
}
