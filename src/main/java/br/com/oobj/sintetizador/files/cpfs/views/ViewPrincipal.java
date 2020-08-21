package br.com.oobj.sintetizador.files.cpfs.views;

import br.com.oobj.sintetizador.files.cpfs.service.IProcessarArquivoService;
import br.com.oobj.sintetizador.files.cpfs.service.ProcessarArquivoService;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ViewPrincipal extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1L;
    private Container container;

    private JLabel labelVacuo;
//    private JLabel labelVacuo;
//    private JLabel labelVacuo;

    private JTextField txtDiretorioPathEntrada;
    private JTextField txtDiretorioPathDestino;
    private JTextField txtDiretorioPathMoveArquivos;

    private JButton btBuscalDiretorioPathEntrada;
    private JButton btBuscalDiretorioPathDestino;
    private JButton btBuscalDiretorioMoveArquivos;
    private JButton btProcessar;

    private JMenuBar menuOption;
    private JMenuItem optionSair;

    private JMenu menuAjuda;
    private JMenuItem optionTopico;
    private JMenuItem optionSobre;

    final IProcessarArquivoService processarArquivoService;

    public ViewPrincipal() {
        super("Leitor CPFS in XML");
        this.processarArquivoService = new ProcessarArquivoService();

        this.container = new Container();
        this.container = getContentPane();
        this.container.setLayout(null);
        this.container.setLayout(null);
        this.container.setLayout(null);

        inicializarMenu();
        inicializarComponentes();

        this.setSize(400, 400);
        this.setTitle("Leitor CPFS in XML");
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("calc.gif").getImage());
    }

    private void inicializarMenu() {
        this.menuOption = new JMenuBar();
        this.setJMenuBar(menuOption);

        this.optionSair = new JMenuItem("Sair ");
        this.optionSair.setMnemonic('S');
        EventosCalculadora eventoMenuSair = new EventosCalculadora();
        this.optionSair.addActionListener(eventoMenuSair);

        this.menuAjuda = new JMenu("Ajuda");
        this.menuAjuda.setMnemonic('A');
        this.optionSobre = new JMenuItem("Sobre");
        this.optionSobre.setMnemonic('S');
        menuAjuda.add(optionSobre);

        this.menuOption.add(menuAjuda);
        setJMenuBar(menuOption);
    }

    private void inicializarComponentes() {
        this.txtDiretorioPathEntrada = new JTextField(12);
        this.txtDiretorioPathEntrada.setBorder(new LineBorder(Color.BLACK));
        this.txtDiretorioPathEntrada.setFont(new Font("Tahoma", Font.PLAIN, 11));
        this.txtDiretorioPathEntrada.setHorizontalAlignment(JTextField.RIGHT);
        this.txtDiretorioPathEntrada.setEditable(true);
        this.txtDiretorioPathEntrada.setFocusable(false);
        this.txtDiretorioPathEntrada.setBounds(8, 5, 235, 25);
        this.container.add(txtDiretorioPathEntrada);

        this.txtDiretorioPathDestino = new JTextField(12);
        this.txtDiretorioPathDestino.setBorder(new LineBorder(Color.BLACK));
        this.txtDiretorioPathDestino.setFont(new Font("Tahoma", Font.PLAIN, 11));
        this.txtDiretorioPathDestino.setHorizontalAlignment(JTextField.RIGHT);
        this.txtDiretorioPathDestino.setEditable(true);
        this.txtDiretorioPathDestino.setFocusable(false);
        this.txtDiretorioPathDestino.setBounds(8, 5, 235, 25);
        this.container.add(txtDiretorioPathDestino);

        this.labelVacuo = new JLabel();
        this.labelVacuo.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        this.labelVacuo.setBounds(5, 35, 30, 30);
        this.container.add(labelVacuo);

        this.btBuscalDiretorioPathEntrada = new JButton("<html><font color=#FF0000> Buscar In");
        EventosCalculadora eventoLimpa0 = new EventosCalculadora();
        this.btBuscalDiretorioPathEntrada.addActionListener(eventoLimpa0);
        this.btBuscalDiretorioPathEntrada.setBounds(50, 35, 80, 30);
        this.container.add(btBuscalDiretorioPathEntrada);

        this.btBuscalDiretorioPathDestino = new JButton("<html><font color=#FF0000> Buscar Out");
        EventosCalculadora eventoLimpa = new EventosCalculadora();
        this.btBuscalDiretorioPathDestino.addActionListener(eventoLimpa);
        this.btBuscalDiretorioPathDestino.setBounds(130, 35, 60, 30);
        this.container.add(btBuscalDiretorioPathDestino);

        this.btBuscalDiretorioMoveArquivos = new JButton("<html><font color=#FF0000> Buscar Mover");
        EventosCalculadora eventoLimpa2 = new EventosCalculadora();
        this.btBuscalDiretorioMoveArquivos.addActionListener(eventoLimpa2);
        this.btBuscalDiretorioMoveArquivos.setBounds(190, 35, 60, 30);
        this.container.add(btBuscalDiretorioMoveArquivos);
    }

    private class EventosCalculadora implements ActionListener {
        public void actionPerformed(ActionEvent eventos) {
            if (eventos.getSource() == optionSair) {
                System.exit(0);
            }

            if (eventos.getSource() == txtDiretorioPathEntrada) {
                if (txtDiretorioPathEntrada.getText().isEmpty()) {
                }
            }

            if (eventos.getSource() == btBuscalDiretorioPathEntrada) {

            }

            if (eventos.getSource() == btBuscalDiretorioPathDestino) {

            }

            if (eventos.getSource() == btBuscalDiretorioMoveArquivos) {

            }

            if (eventos.getSource() == btProcessar) {
                efetuarProcessamentoAPartirPaths();
            }
        }
    }

    private void efetuarProcessamentoAPartirPaths() {
        String strPathEntrada = this.txtDiretorioPathEntrada.getText();
        String strPathDestino = this.txtDiretorioPathDestino.getText();
        String strPathArquivosMove = this.txtDiretorioPathMoveArquivos.getText();
        // this.processarArquivoService.efetuarProcessamentoPor(strPathEntrada, strPathDestino, strPathArquivosMove);
    }

    public void keyTyped(KeyEvent e) {
        if ((e.getKeyChar() >= '0') && (e.getKeyChar() <= '9')) {
            this.txtDiretorioPathEntrada.setText(this.txtDiretorioPathEntrada.getText() + e.getKeyChar());
        }
        if ((e.getKeyChar() >= '0') && (e.getKeyChar() <= '9')) {
            this.txtDiretorioPathEntrada.setText(this.txtDiretorioPathEntrada.getText() + e.getKeyChar());
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource() == this.txtDiretorioPathEntrada) {
            executarEventoTxtPathDiretorioEntrada();
        }
        if (e.getSource() == this.txtDiretorioPathDestino) {
            executarEventoTxtPathDiretorioDestino();
        }
        if (e.getSource() == this.txtDiretorioPathMoveArquivos) {
            executarEventotxtDiretorioPathMoveArquivos();
        }
    }

    public void keyReleased(KeyEvent eventoTeclado3) {

    }

    private void executarEventoTxtPathDiretorioEntrada() {
        if (this.txtDiretorioPathEntrada.getText().isEmpty()) {
            this.txtDiretorioPathEntrada.setFocusable(true);
            return;
        }
        this.txtDiretorioPathDestino.setFocusable(true);
    }

    private void executarEventoTxtPathDiretorioDestino() {
        if (this.txtDiretorioPathDestino.getText().isEmpty()) {
            this.txtDiretorioPathDestino.setFocusable(true);
            return;
        }
        this.txtDiretorioPathMoveArquivos.setFocusable(true);
    }

    private void executarEventotxtDiretorioPathMoveArquivos() {
        if (this.txtDiretorioPathMoveArquivos.getText().isEmpty()) {
            this.txtDiretorioPathMoveArquivos.setFocusable(true);
            return;
        }
        this.btProcessar.setFocusable(true);
    }
}
