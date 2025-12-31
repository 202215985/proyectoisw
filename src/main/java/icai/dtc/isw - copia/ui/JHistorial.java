package icai.dtc.isw.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Partida;
import icai.dtc.isw.domain.ResultadoPartida;
import icai.dtc.isw.domain.Usuario;

public class JHistorial extends JPanel {

    private JSesion ventanaPrincipal;
    private Usuario usu;
    private ArrayList<Partida> partidas;

    public JHistorial(JSesion ventanaPrincipal, Usuario usu) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.usu = usu;

        setLayout(new GridBagLayout());
        setOpaque(false);

        // Título
        JLabel titulo = new JLabel("Historial de Partidas");
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setHorizontalAlignment(JLabel.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titulo, gbc);

        partidas = getPartidas(usu.getDni());
        System.out.println("EL tamaño del array de las partidas es " +partidas.size());

        // Crear panel contenedor para las partidas
        JPanel contenedorPartidas = new JPanel(new GridBagLayout());
        contenedorPartidas.setOpaque(false);

        GridBagConstraints gbcCont = new GridBagConstraints();
        gbcCont.gridx = 0;
        gbcCont.weightx = 1.0;
        gbcCont.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < partidas.size(); i++) {
            gbcCont.gridy = i * 2;
            JPanel panelPartida = crearPanelPartida(partidas.get(i), i);
            contenedorPartidas.add(panelPartida, gbcCont);

            if (i < partidas.size() - 1) {
                gbcCont.gridy = i * 2 + 1;
                JPanel espacio = crearEspacio();
                contenedorPartidas.add(espacio, gbcCont);
            }
        }

        // Crear JScrollPane
        JScrollPane scrollPane = new JScrollPane(contenedorPartidas);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Agregar scrollPane
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);

        // Botón Volver
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> ventanaPrincipal.mostrarPerfil(usu));
        add(btnVolver, gbc);
    }

    private JPanel crearPanelPartida(Partida par, int index) {
        JPanel panel = new JPanel(new BorderLayout());
        StringBuilder mensaje = new StringBuilder();
        panel.setOpaque(true);
        if (index % 2 == 0) {
            panel.setBackground(new Color(144, 238, 144)); // Verde claro para pares
        } else {
            panel.setBackground(new Color(34, 139, 34)); // Verde más oscuro para impares
        }

        // Panel para los labels
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        labelsPanel.setOpaque(false);

        // Fecha
        labelsPanel.add(new JLabel("Fecha: " + par.getFecha()));

        // Campo
        labelsPanel.add(new JLabel("Campo: " + par.getCampo().getNombre()));

        // Resultado
        labelsPanel.add(new JLabel("Resultado: " + par.getResultado()));

        // Par
        labelsPanel.add(new JLabel("Par: " + par.getCampo().getPar()));

        // Diferencia
        int diferencia = par.getResultado() - par.getCampo().getPar();
        String signo = (diferencia > 0) ? "+" : "";
        labelsPanel.add(new JLabel("Diferencia: " + signo + diferencia));

        panel.add(labelsPanel, BorderLayout.CENTER);

        // Botón para ver ID
        JButton btnId = new JButton("Ver golpes");
        
        ArrayList<ResultadoPartida> resultados = getResultados(par);
        
        int numHoyo=1;
        for (ResultadoPartida r : resultados) {
            mensaje.append("Hoyo: " + numHoyo++ + ", Golpes: " + r.getNumGolpes() + "\n");
        }
        btnId.addActionListener(e -> JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE));
        panel.add(btnId, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearEspacio() {
        JPanel espacio = new JPanel();
        espacio.setOpaque(false);
        espacio.setPreferredSize(new java.awt.Dimension(0, 10));
        return espacio;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Partida> getPartidas(String dni_usu){
        Client cliente;
        cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getPartidas";
        session.put("dni_usu", dni_usu);
        session = cliente.sentMessage(context, session);
        ArrayList<Partida> p = (ArrayList<Partida>) session.get("Partidas");   
        return p;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<ResultadoPartida> getResultados(Partida partida){
        Client cliente;
        cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getResultados";
        session.put("Partida", partida);
        session = cliente.sentMessage(context, session);
        ArrayList<ResultadoPartida> r = (ArrayList<ResultadoPartida>) session.get("Resultados");
        if (r == null) {
            System.out.println("Advertencia: Resultados null en respuesta del servidor");
            return new ArrayList<>();
        }
        return r;
    }
}
