package icai.dtc.isw.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblBienvenida, gbc);

        // === Botones (centrados) ===
        JButton btnJugar = new JButton("Jugar partida");
        JButton btnPalos = new JButton("Distancia de palos");
        JButton btnEstadisticas = new JButton("Estadísticas");
        JButton btnVolver = new JButton("Volver al menú");

        btnJugar.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPalos.setFont(new Font("Arial", Font.PLAIN, 16));
        btnEstadisticas.setFont(new Font("Arial", Font.PLAIN, 16));
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));

        // Ancho uniforme
        int anchoBoton = 220;
        int altoBoton = 40;
        btnJugar.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));
        btnPalos.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));
        btnEstadisticas.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));
        btnVolver.setPreferredSize(new java.awt.Dimension(anchoBoton, altoBoton));

        gbc.gridy = 1;
        add(btnJugar, gbc);

        gbc.gridy = 2;
        add(btnPalos, gbc);

        gbc.gridy = 3;
        add(btnEstadisticas, gbc);

        gbc.gridy = 4;
        add(btnVolver, gbc);

        // === Acciones ===
        btnVolver.addActionListener(e -> ventanaPrincipal.mostrarMenu());

        btnJugar.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "¡A calentar!\nTu partida comenzará pronto.",
                        "Modo Juego",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        btnPalos.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Consulta la distancia ideal de cada palo",
                        "Distancia de Palos",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        btnEstadisticas.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Visualiza tus estadísticas de juego",
                        "Estadísticas del Jugador",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );
    }
}

