package icai.dtc.isw.ui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Usuario;

public class JVentanaUsu extends JPanel{

    public JVentanaUsu(JSesion ventanaPrincipal){
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gr = new GridBagConstraints();
        gr.insets = new Insets(5, 5, 5, 5);
        gr.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblRegTitulo = new JLabel("Registro de Usuario", SwingConstants.CENTER);
        lblRegTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblRegTitulo.setForeground(java.awt.Color.WHITE);

        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(20);

        JLabel lblApellidos = new JLabel("Apellidos:");
        JTextField txtApellidos = new JTextField(20);

        JLabel lblDniR = new JLabel("DNI:");
        JTextField txtDniR = new JTextField(20);

        JLabel lblCorreoR = new JLabel("Correo:");
        JTextField txtCorreoR = new JTextField(20);

        JLabel lblPass = new JLabel("Contraseña:");
        JPasswordField txtPass = new JPasswordField(20);
        txtPass.setEchoChar('●');

        JButton btnVolverRegistro = new JButton("Volver");
        JButton btnRegistrar = new JButton("Registrar");
        
        btnVolverRegistro.addActionListener(e -> ventanaPrincipal.mostrarMenu());

        // Acción de registro → crea el objeto Usuario y lo guarda en BD
        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String dni = txtDniR.getText().trim();
            String correo = txtCorreoR.getText().trim();
            String password = new String(txtPass.getPassword()).trim();

            // Validar que los campos no estén vacíos
            if (nombre.isEmpty() || apellidos.isEmpty() || dni.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos.");
                return;
            }

            Usuario existente = ventanaPrincipal.recuperarUsuario(dni);
            if (existente!=null) {
            JOptionPane.showMessageDialog(this,
                "⚠️ Este usuario ya tiene una cuenta.\nPor favor, inicia sesión.",
                "Usuario existente",
                JOptionPane.WARNING_MESSAGE);
                txtNombre.setText("");
                txtApellidos.setText("");
                txtDniR.setText("");
                txtCorreoR.setText("");
                txtPass.setText("");
                ventanaPrincipal.mostrarLogin();
            }
            else{
                Usuario nuevo = new Usuario(nombre, apellidos, correo, dni, password);
                if (addUsuario(nuevo)!= null){
                JOptionPane.showMessageDialog(this, "✅ Usuario registrado correctamente.");
                txtNombre.setText("");
                txtApellidos.setText("");
                txtDniR.setText("");
                txtCorreoR.setText("");
                txtPass.setText("");
                ventanaPrincipal.mostrarMenu();
                
                }
                else{
                    JOptionPane.showMessageDialog(this, "✅ Usuario no registrado correctamente.");
                }
            }

        });

        gr.gridx = 0; gr.gridy = 0; gr.gridwidth = 2;
        this.add(lblRegTitulo, gr);

        gr.gridwidth = 1;
        gr.gridy++;
        this.add(lblNombre, gr);
        gr.gridx = 1;
        this.add(txtNombre, gr);

        gr.gridx = 0; gr.gridy++;
        this.add(lblApellidos, gr);
        gr.gridx = 1;
        this.add(txtApellidos, gr);

        gr.gridx = 0; gr.gridy++;
        this.add(lblDniR, gr);
        gr.gridx = 1;
        this.add(txtDniR, gr);

        gr.gridx = 0; gr.gridy++;
        this.add(lblCorreoR, gr);
        gr.gridx = 1;
        this.add(txtCorreoR, gr);

        gr.gridx = 0; gr.gridy++;
        this.add(lblPass, gr);
        gr.gridx = 1;
        this.add(txtPass, gr);

        gr.gridx = 0; gr.gridy++; gr.gridwidth = 2;
        this.add(btnRegistrar, gr);

        gr.gridy++;
        this.add(btnVolverRegistro, gr);
   
    }


    public Usuario addUsuario(Usuario usu){
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/setUsuario";
        session.put("Usuario", usu);
        session = cliente.sentMessage(context, session);
        Usuario usu_nuevo = (Usuario) session.get("Usuario");
        return usu_nuevo;
    }
}
