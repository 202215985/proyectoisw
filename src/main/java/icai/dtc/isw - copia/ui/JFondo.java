package icai.dtc.isw.ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JFondo extends JPanel {
    private Image imagen;

    public JFondo(String pathImagen) {
        // Carga la imagen desde el classpath
        java.net.URL url = getClass().getResource(pathImagen);
        if (url == null) {
            System.err.println("⚠️ No se encontró la imagen: " + pathImagen);
        } else {
            this.imagen = new ImageIcon(url).getImage();
        }
        setLayout(new BorderLayout()); // importante para contener otros paneles
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            // Escala la imagen al tamaño del panel
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

