package tarea.topicos;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CarreraCaballos extends JFrame {

    private JPanel[] pistas;
    private JButton iniciarCarrera, reiniciarCarrera;
    private JLabel[] caballos;
    private Timer timer;
    private Random random;

    public CarreraCaballos() {
        super("Carrera de Caballos");
        setLayout(new BorderLayout());
        random = new Random();

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(5, 1)); // 5 carriles (caballos)

        // Inicializar pistas y caballos
        pistas = new JPanel[5];
        caballos = new JLabel[5];

        for (int i = 0; i < 5; i++) {
            pistas[i] = new JPanel();
            pistas[i].setLayout(new FlowLayout(FlowLayout.LEFT)); // Los caballos inician a la izquierda
            caballos[i] = new JLabel("Caballo " + (i + 1));
            pistas[i].add(caballos[i]);
            panelPrincipal.add(pistas[i]);
        }

        // Botón para iniciar la carrera
        iniciarCarrera = new JButton("Iniciar Carrera");
        iniciarCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarCarrera.setEnabled(false); // Desactivar el botón durante la carrera
                reiniciarCarrera.setEnabled(false); // Desactivar reinicio durante la carrera
                comenzarCarrera();
            }
        });

        // Botón para reiniciar la carrera
        reiniciarCarrera = new JButton("Reiniciar Carrera");
        reiniciarCarrera.setEnabled(false); // Inicia deshabilitado hasta que termine la carrera
        reiniciarCarrera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarPosiciones(); // Restaurar posiciones iniciales
                iniciarCarrera.setEnabled(true); // Habilitar el botón para iniciar una nueva carrera
                reiniciarCarrera.setEnabled(false); // Desactivar reinicio hasta que termine una carrera
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(iniciarCarrera);
        panelBotones.add(reiniciarCarrera);

        add(panelPrincipal, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Configuración de la ventana
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CarreraCaballos();
    }

    // Método para comenzar la carrera
    private void comenzarCarrera() {
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean carreraEnCurso = true;
                for (int i = 0; i < caballos.length; i++) {
                    int avance = random.nextInt(20); // Avance aleatorio entre 0 y 20
                    caballos[i].setLocation(caballos[i].getLocation().x + avance, caballos[i].getLocation().y);

                    if (caballos[i].getLocation().x >= 700) { // Meta al final del panel
                        ((Timer) e.getSource()).stop(); // Detener el timer
                        JOptionPane.showMessageDialog(null, "¡El caballo " + (i + 1) + " ha ganado!");
                        carreraEnCurso = false;
                        break;
                    }
                }

                if (!carreraEnCurso) {
                    iniciarCarrera.setEnabled(false); // Deshabilitar hasta que se reinicie la carrera
                    reiniciarCarrera.setEnabled(true); // Habilitar reinicio
                }
            }
        });
        timer.start();
    }

    // Método para reiniciar las posiciones de los caballos
    private void reiniciarPosiciones() {
        for (JLabel caballo : caballos) {
            caballo.setLocation(0, caballo.getLocation().y); // Restablecer al inicio
        }
    }
}