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

public class JVentanaLogin extends JPanel {

    public JVentanaLogin(JSesion ventanaPrincipal){
        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gl = new GridBagConstraints();
        gl.insets = new Insets(5, 5, 5, 5);
        gl.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLoginTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblLoginTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel lblDni = new JLabel("DNI:");
        JTextField txtDni = new JTextField(20);

        JLabel lblPass = new JLabel("Contraseña:");
        JPasswordField txtPass = new JPasswordField(20);
        txtPass.setEchoChar('●');

        JButton btnVolverLogin = new JButton("Volver");
        JButton btnEntrar = new JButton("Entrar");

        gl.gridx = 0; gl.gridy = 0; gl.gridwidth = 2;
        this.add(lblLoginTitulo, gl);

        gl.gridwidth = 1;
        gl.gridy++;
        this.add(lblDni, gl);
        gl.gridx = 1;
        this.add(txtDni, gl);

        gl.gridx = 0; gl.gridy++;
        this.add(lblPass, gl);
        gl.gridx = 1;
        this.add(txtPass, gl);

        gl.gridx = 0; gl.gridy++; gl.gridwidth = 2;
        this.add(btnEntrar, gl);

        gl.gridy++;
        this.add(btnVolverLogin, gl);

        btnVolverLogin.addActionListener(e ->{ 
            ventanaPrincipal.mostrarMenu();
            txtDni.setText("");
            txtPass.setText("");
            }
        );

        btnEntrar.addActionListener(e -> {
            String dni = txtDni.getText().trim();
            String password = new String(txtPass.getPassword()).trim();
            
            if (dni.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, rellena todos los campos.");
                return;
            }

            Usuario existente = login(dni,password);
            Usuario ex = ventanaPrincipal.recuperarUsuario(dni);
            if (existente != null) {
                ventanaPrincipal.setUsuario_actual(existente);
                ventanaPrincipal.mostrarPerfil(existente);
                txtDni.setText("");
                txtPass.setText("");
            }

            else if (ex != null && !ex.getPassword().equals(password)){
                JOptionPane.showMessageDialog(this,
                "⚠️ Contraseña incorrecta, comprueba tus datos.",
                "Inicio de sesión incorrecto",
                JOptionPane.WARNING_MESSAGE);
                txtPass.setText("");
                
            }
            else{
                JOptionPane.showMessageDialog(this,
                "⚠️ Este usuario no existe. Por favor, registrate para iniciar sesion.",
                "Inicio de sesión incorrecto",
                JOptionPane.WARNING_MESSAGE);
                txtDni.setText("");
                txtPass.setText("");
                ventanaPrincipal.mostrarRegistro();  // Lo lleva al registro
            //return;
            }
        });

    
    }

    public Usuario login(String dni, String password){
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/login";
        session.put("dni", dni);
        session.put("password", password);
        session = cliente.sentMessage(context, session);
        Usuario usu = (Usuario) session.get("Usuario");
        return usu;
    }

    
}
