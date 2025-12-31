package icai.dtc.isw.ui;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Usuario;

public class JSesion extends JFrame {

    private JPanel mainPanel;     
    public JPanel menuPanel;     
    public JPanel loginPanel;    
    //private JPanel panelUsuarios; 
    public CardLayout cardLayout;

    private Usuario usuario_actual;

    public JSesion() {
        usuario_actual=null;
        
        setTitle("HoleIn1cai");
        setSize(450, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JFondo("/fondo.jpg");
        mainPanel.setLayout(cardLayout);

        // ===== PANEL MENÚ =====
        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("HoleIn1cai", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnRegistro = new JButton("Registrarse");
        JButton btnInfoCampos = new JButton("Información de campos");

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        menuPanel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        menuPanel.add(btnLogin, gbc);

        gbc.gridy++;
        menuPanel.add(btnRegistro, gbc);

        gbc.gridy++;
        menuPanel.add(btnInfoCampos, gbc);


        // PANEL INFO CAMPOS

        JVentanaCampos panelCampos = new JVentanaCampos(this);


        // ===== PANEL LOGIN =====
        JVentanaLogin panelLogin = new JVentanaLogin(this);
        

        // ===== PANEL REGISTRO =====
        JVentanaUsu panelUsuarios = new JVentanaUsu(this);
        

        // ===== AÑADIR LOS PANELES =====
        
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(panelLogin, "login");
        mainPanel.add(panelUsuarios, "registro");
        mainPanel.add(panelCampos, "campos");
        menuPanel.setOpaque(false);
        panelLogin.setOpaque(false);
        panelUsuarios.setOpaque(false);

        add(mainPanel);

        // ===== ACCIONES =====
        btnLogin.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        btnInfoCampos.addActionListener(e -> cardLayout.show(mainPanel, "campos"));
        btnRegistro.addActionListener(e -> cardLayout.show(mainPanel, "registro"));
       

    }

    public void mostrarMenu() {
        cardLayout.show(mainPanel, "menu");
    }

    public void mostrarLogin() {
        JVentanaLogin menuLogin = new JVentanaLogin(this);
        mainPanel.add(menuLogin,"menuLogin");
        cardLayout.show(mainPanel,"menuLogin");
    }

    public void mostrarRegistro() {
        JVentanaUsu menuReg = new JVentanaUsu(this);
        mainPanel.add(menuReg,"menuRegistro");
        cardLayout.show(mainPanel,"menuRegistro");
    }

    public void mostrarPerfil(Usuario usu) {
        JUsuario menuUsu = new JUsuario(this, usu);
        mainPanel.add(menuUsu, "menuUsuario");
        cardLayout.show(mainPanel, "menuUsuario");
    }
    
    public void mostrarPartida(Usuario usu) {
        JPartida partida = new JPartida(this, usu);
        mainPanel.add(partida, "menuPartida");
        cardLayout.show(mainPanel, "menuPartida");
    }

    public void mostrarHistorial(Usuario usu){
        JHistorial historial = new JHistorial(this,usu);
        mainPanel.add(historial,"menuHistorial");
        cardLayout.show(mainPanel,"menuHistorial");
    }

    public Usuario recuperarUsuario(String dni) {
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getUsuario";
        session.put("dni", dni);
        session = cliente.sentMessage(context, session);
        Usuario usu = (Usuario) session.get("Usuario");
        return usu;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JSesion().setVisible(true));
    }

    public Usuario getUsuario_actual() {
        return usuario_actual;
    }

    public void setUsuario_actual(Usuario usuario_actual) {
        this.usuario_actual = usuario_actual;
    }
    
}