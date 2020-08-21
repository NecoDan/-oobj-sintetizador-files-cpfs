package br.com.oobj.sintetizador.files.cpfs.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemoJFileChooser extends JPanel implements ActionListener {

    JButton buscar;
    JFileChooser jFileChooser;
    String titulo;

    public DemoJFileChooser() {
        buscar = new JButton("Do it");
        buscar.addActionListener(this);
        add(buscar);
    }

    public void actionPerformed(ActionEvent e) {
        jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new java.io.File("."));
        jFileChooser.setDialogTitle(titulo);
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooser.setAcceptAllFileFilterUsed(false);

        if (jFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + jFileChooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + jFileChooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }
}
