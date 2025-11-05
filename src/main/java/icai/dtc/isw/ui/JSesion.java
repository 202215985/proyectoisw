package icai.dtc.isw.ui;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import icai.dtc.isw.dao.ConnectionDAO;
import icai.dtc.isw.domain.Usuario;

public class JSesion extends JFrame {

    private JPanel mainPanel;     
    private JPanel menuPanel;     
    private JPanel loginPanel;    
    private JPanel registroPanel; 
    private CardLayout cardLayout;

    public JSesion() {
        setTitle("Inicio / Registro de Usuario");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // ===== PANEL MENÚ =====
        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Bienvenido", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        menuPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        menuPanel.add(btnLogin, gbc);

        gbc.gridy++;
        menuPanel.add(btnRegistro, gbc);

        // ===== PANEL LOGIN =====
        loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gl = new GridBagConstraints();
        gl.insets = new Insets(5, 5, 5, 5);
        gl.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLoginTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblLoginTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDni = new JLabel("DNI:");
        JTextField txtDni = new JTextField(20);

        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField(20);

        JButton btnVolverLogin = new JButton("Volver");
        JButton btnEntrar = new JButton("Entrar");

        gl.gridx = 0; gl.gridy = 0; gl.gridwidth = 2;
        loginPanel.add(lblLoginTitulo, gl);

        gl.gridwidth = 1;
        gl.gridy++;
        loginPanel.add(lblDni, gl);
        gl.gridx = 1;
        loginPanel.add(txtDni, gl);

        gl.gridx = 0; gl.gridy++;
        loginPanel.add(lblCorreo, gl);
        gl.gridx = 1;
        loginPanel.add(txtCorreo, gl);

        gl.gridx = 0; gl.gridy++; gl.gridwidth = 2;
        loginPanel.add(btnEntrar, gl);

        gl.gridy++;
        loginPanel.add(btnVolverLogin, gl);

        // ===== PANEL REGISTRO =====
        registroPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gr = new GridBagConstraints();
        gr.insets = new Insets(5, 5, 5, 5);
        gr.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRegTitulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        lblRegTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(20);

        JLabel lblApellidos = new JLabel("Apellidos:");
        JTextField txtApellidos = new JTextField(20);

        JLabel lblDniR = new JLabel("DNI:");
        JTextField txtDniR = new JTextField(20);

        JLabel lblCorreoR = new JLabel("Correo:");
        JTextField txtCorreoR = new JTextField(20);

        JButton btnVolverRegistro = new JButton("Volver");
        JButton btnRegistrar = new JButton("Registrar");

        gr.gridx = 0; gr.gridy = 0; gr.gridwidth = 2;
        registroPanel.add(lblRegTitulo, gr);

        gr.gridwidth = 1;
        gr.gridy++;
        registroPanel.add(lblNombre, gr);
        gr.gridx = 1;
        registroPanel.add(txtNombre, gr);

        gr.gridx = 0; gr.gridy++;
        registroPanel.add(lblApellidos, gr);
        gr.gridx = 1;
        registroPanel.add(txtApellidos, gr);

        gr.gridx = 0; gr.gridy++;
        registroPanel.add(lblDniR, gr);
        gr.gridx = 1;
        registroPanel.add(txtDniR, gr);

        gr.gridx = 0; gr.gridy++;
        registroPanel.add(lblCorreoR, gr);
        gr.gridx = 1;
        registroPanel.add(txtCorreoR, gr);

        gr.gridx = 0; gr.gridy++; gr.gridwidth = 2;
        registroPanel.add(btnRegistrar, gr);

        gr.gridy++;
        registroPanel.add(btnVolverRegistro, gr);

        // ===== AÑADIR LOS PANELES =====
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(loginPanel, "login");
        mainPanel.add(registroPanel, "registro");

        add(mainPanel);

        // ===== ACCIONES =====
        btnLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        btnRegistro.addActionListener(e -> cardLayout.show(mainPanel, "registro"));
        btnVolverLogin.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        btnVolverRegistro.addActionListener(e -> cardLayout.show(mainPanel, "menu"));
        
        // Acción de registro → crea el objeto Usuario y lo guarda en BD
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String dni = txtDniR.getText().trim();
            String correo = txtCorreoR.getText().trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || correo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos.");
                return;
            }

            // Crear el objeto Usuario
            Usuario nuevo = new Usuario(dni, nombre, apellidos, correo);

            // Guardar en base de datos
            setCliente(nuevo);

            JOptionPane.showMessageDialog(this, "✅ Usuario registrado correctamente.");
            cardLayout.show(mainPanel, "menu");

            // Limpiar campos
            txtNombre.setText("");
            txtApellidos.setText("");
            txtDniR.setText("");
            txtCorreoR.setText("");
        });
    }

    // =====================================================
    // MÉTODO PARA GUARDAR USUARIO EN LA BASE DE DATOS
    // =====================================================
    public void setCliente(Usuario usu) {
        Connection con = ConnectionDAO.getInstance().getConnection();
        String insertar = "INSERT INTO usuarios (dni, nombre, apellidos, correo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = con.prepareStatement(insertar)) {
            pst.setString(1, usu.getDni());
            pst.setString(2, usu.getNombre());
            pst.setString(3, usu.getApellidos());
            pst.setString(4, usu.getCorreo());

            int filas = pst.executeUpdate();

            if (filas > 0) {
                System.out.println("Usuario insertado correctamente en la base de datos.");
            } else {
                System.out.println("No se pudo insertar el usuario.");
            }

        } catch (SQLException ex) {
            System.out.println("Error al insertar el usuario: " + ex.getMessage());
        }
    }
        

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JSesion().setVisible(true));
    }
}