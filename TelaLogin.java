package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TelaLogin extends JFrame {
    public static String operadorLogado;

    private static final Color COLOR_BG = new Color(30, 33, 36);
    private static final Color COLOR_SURFACE = new Color(40, 44, 48);
    private static final Color COLOR_ACCENT = new Color(0, 123, 255);
    private static final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private static final Color COLOR_TEXT_PRIMARY = new Color(245, 245, 245);
    private static final Color COLOR_TEXT_MUTED = new Color(170, 175, 180);

    private JTextField userField;
    private JPasswordField passField;

    public TelaLogin() {
        setTitle("SISTEMA PDV - ACESSO");
        setSize(420, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_BG);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(COLOR_ACCENT);
        headerPanel.setPreferredSize(new Dimension(420, 80));
        JLabel titleLabel = new JLabel("FRENTE DE LOJA");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerPanel.add(titleLabel);

        JPanel bodyPanel = new JPanel(new GridBagLayout());
        bodyPanel.setBackground(COLOR_BG);
        bodyPanel.setBorder(new EmptyBorder(25, 45, 25, 45));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel userLabel = new JLabel("OPERADOR:");
        userLabel.setForeground(COLOR_TEXT_MUTED);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 6, 0);
        bodyPanel.add(userLabel, gbc);

        userField = createCustomTextField();
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 20, 0);
        bodyPanel.add(userField, gbc);

        JLabel passLabel = new JLabel("SENHA DE ACESSO:");
        passLabel.setForeground(COLOR_TEXT_MUTED);
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridy = 2; gbc.insets = new Insets(0, 0, 6, 0);
        bodyPanel.add(passLabel, gbc);

        passField = new JPasswordField();
        setupFieldStyle(passField);
        gbc.gridy = 3; gbc.insets = new Insets(0, 0, 35, 0);
        bodyPanel.add(passField, gbc);

        JButton enterButton = new JButton("ENTRAR NO SISTEMA");
        enterButton.setBackground(COLOR_SUCCESS);
        enterButton.setForeground(Color.WHITE);
        enterButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        enterButton.setFocusPainted(false);
        enterButton.setBorderPainted(false);
        enterButton.setPreferredSize(new Dimension(0, 48));
        gbc.gridy = 4; gbc.insets = new Insets(0, 0, 0, 0);
        bodyPanel.add(enterButton, gbc);

        enterButton.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword());
            if (user.equalsIgnoreCase("admin") && pass.equals("123")) {
                operadorLogado = user.toUpperCase();
                new TelaPDV().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciais Inválidas!", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTextField createCustomTextField() {
        JTextField f = new JTextField();
        setupFieldStyle(f);
        return f;
    }

    private void setupFieldStyle(JTextField f) {
        f.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        f.setForeground(COLOR_TEXT_PRIMARY);
        f.setBackground(COLOR_SURFACE);
        f.setCaretColor(COLOR_TEXT_PRIMARY);
        f.setPreferredSize(new Dimension(0, 38));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(65, 71, 78), 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
    }
}