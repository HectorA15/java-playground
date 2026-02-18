package gui.tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui1 extends JFrame {
    JLabel pregunta;
    JRadioButton respuestaSi;
    JRadioButton respuestaNo;
    JButton aceptar;

    public gui1() {
        //Frame
        super("Sondeo de opinion");
        this.setLayout(new FlowLayout());

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));


        pregunta = new JLabel("¿Entendiste la clase?");
        pregunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(pregunta);
        panelPrincipal.add(Box.createVerticalStrut(10));

        JPanel panelOpciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        respuestaSi = new JRadioButton("Si");
        respuestaNo = new JRadioButton("No");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(respuestaSi);
        grupo.add(respuestaNo);

        panelOpciones.add(respuestaSi);
        panelOpciones.add(respuestaNo);
        panelPrincipal.add(panelOpciones);
        panelPrincipal.add(Box.createVerticalStrut(10));

        aceptar = new JButton("Aceptar");
        aceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JDialog.setDefaultLookAndFeelDecorated(true);
                if (respuestaSi.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Okey, entonces sigamos", "Si entendiste!", JOptionPane.INFORMATION_MESSAGE);
                } else if (respuestaNo.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Toca estudiar", "No entendiste...", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        aceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(aceptar);

        this.add(panelPrincipal);
        setPreferredSize(new Dimension(300, 200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();

    }

    public static void main(String[] args) {
        gui1 app = new gui1();
        app.setVisible(true);


    }

}