package gui;

import javax.swing.*;
import java.awt.*;


public class SwingExample extends JFrame {

    public SwingExample() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 300));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JLabel logIn = new JLabel("Log into your account");
        logIn.setFont(new Font("Arial", Font.BOLD, 20));
        logIn.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(new Color(255, 255, 255));
        GridBagConstraints fieldsGbc = new GridBagConstraints();
        fieldsGbc.insets = new Insets(5, 5, 5, 5); // margin 5px

        // username label (column 0, row 0)
        fieldsGbc.gridx = 0;
        fieldsGbc.gridy = 0;
        fieldsGbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(new JLabel("Username:"), fieldsGbc);

        // username field (column 1, row 0)
        fieldsGbc.gridx = 1;
        fieldsGbc.gridy = 0;
        fieldsGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldsGbc.weightx = 1;
        fieldsPanel.add(new JTextField(20), fieldsGbc);

        // password label (column 0, row 1)
        fieldsGbc.gridx = 0;
        fieldsGbc.gridy = 1;
        fieldsGbc.anchor = GridBagConstraints.WEST;
        fieldsPanel.add(new JLabel("Password:"), fieldsGbc);

        // password field (column 1, row 1)
        fieldsGbc.gridx = 1;
        fieldsGbc.gridy = 1;
        fieldsGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldsGbc.weightx = 1;
        fieldsPanel.add(new JPasswordField(20), fieldsGbc);

        fieldsPanel.setMaximumSize(new Dimension(800, 200));

        JButton loginButton = new JButton("Log in");
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFocusable(false);

        panel.add(Box.createVerticalGlue());
        panel.add(logIn, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(30));
        panel.add(fieldsPanel, BorderLayout.CENTER);
        panel.add(Box.createVerticalStrut(30));
        panel.add(loginButton, BorderLayout.SOUTH);
        panel.add(Box.createVerticalGlue());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(255, 255, 255));

        setLayout(new GridBagLayout());
        GridBagConstraints rootGbc = new GridBagConstraints();
        add(panel, rootGbc); // Centra el panel en el JFrame

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setTitle("Swing Example");
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SwingExample());
    }
}
