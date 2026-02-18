package gui.tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui3 extends JFrame implements ActionListener {

    JLabel pregunta;
    JRadioButton respuestaSi;
    JRadioButton respuestaNo;
    JButton aceptar;

    public gui3() {
        super("Sondeo de opinion");
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
        aceptar.setAlignmentX(Component.CENTER_ALIGNMENT);
        aceptar.addActionListener(this); // ← Implementa el ActionListener
        panelPrincipal.add(aceptar);

        this.add(panelPrincipal);

        this.setSize(300, 200);
        this.setLocationRelativeTo(null); // Centrar en pantalla
        this.setVisible(true);
    }

    // Main para probar
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new gui3());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JDialog.setDefaultLookAndFeelDecorated(true);

        if (respuestaSi.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Okey, entonces sigamos",
                    "Si entendiste!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else if (respuestaNo.isSelected()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Toca estudiar",
                    "No entendiste...",
                    JOptionPane.ERROR_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor selecciona una opción",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }
}