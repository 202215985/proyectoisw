package icai.dtc.isw.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import icai.dtc.isw.domain.Usuario;

public class JUsuario extends JPanel {

    private JSesion ventanaPrincipal;
    private Usuario usu;

    public JUsuario(JSesion ventanaPrincipal, Usuario usu) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.usu = usu;

        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.NONE; // NO expandir botones
        gbc.anchor = GridBagConstraints.CENTER; // Centrar todo

        // === Etiqueta de bienvenida ===
        String textoBienvenida = "¡Bienvenido, " + usu.getNombre() + " " + usu.getApellidos() + "!";
        JLabel lblBienvenida = new JLabel(textoBienvenida, SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        lblBienvenida.setForeground(java.awt.Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblBienvenida, gbc);

        // === Botones (centrados) ===
        JButton btnJugar = new JButton("Jugar Partida");
        JButton btnHistorial = new JButton("Historial de Partidas");
        JButton btnVolver = new JButton("Cerrar Sesión");

        btnJugar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnHistorial.setFont(new Font("Arial", Font.PLAIN, 16));
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 16));

        // Ancho uniforme
        int anchoBoton = 220;
        int altoBoton = 40;
        btnJugar.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));
        btnHistorial.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));
        btnVolver.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));

        gbc.gridy = 1;
        add(btnJugar, gbc);

        gbc.gridy = 2;
        add(btnHistorial, gbc);

        gbc.gridy = 3;
        add(btnVolver, gbc);

        // === Acciones ===
        btnVolver.addActionListener(e -> ventanaPrincipal.mostrarMenu());

        btnJugar.addActionListener(e -> ventanaPrincipal.mostrarPartida(usu));

        btnHistorial.addActionListener(e -> ventanaPrincipal.mostrarHistorial(usu));
    }

}

