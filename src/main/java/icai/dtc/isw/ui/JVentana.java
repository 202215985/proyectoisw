
package icai.dtc.isw.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.CampoGolf;

public class JVentana extends JPanel {

    private int id;

    public JVentana(JSesion ventanaPrincipal) {
        super(new BorderLayout());

        // --- Norte: título ---
        JPanel pnlNorte = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Introduzca el ID de campo (1-7)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Courier", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        pnlNorte.add(lblTitulo, BorderLayout.CENTER);
        this.add(pnlNorte, BorderLayout.NORTH);

        // --- Centro: input + botón ---
        JPanel pnlCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 16));
        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();
        txtId.setColumns(10);
        JButton btnInformacion = new JButton("Recibir información");
        pnlCentro.add(lblId);
        pnlCentro.add(txtId);
        pnlCentro.add(btnInformacion);

        // --- Sur: resultado + botón volver ---
        JPanel pnlSur = new JPanel(new BorderLayout(8, 8));
        JLabel lblResultado = new JLabel("El resultado obtenido es:");
        JTextArea txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        JScrollPane scrollResultado = new JScrollPane(txtResultado,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setFont(new Font("Arial", Font.PLAIN, 14));
        GridBagConstraints gr = new GridBagConstraints();
        gr.insets = new Insets(5, 5, 5, 5);
        gr.fill = GridBagConstraints.HORIZONTAL;

        JPanel pnlBotonVolver = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        gr.gridy++;
        pnlBotonVolver.add(btnVolver, gr);

        pnlSur.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        pnlSur.add(lblResultado, BorderLayout.NORTH);
        pnlSur.add(scrollResultado, BorderLayout.CENTER);
        pnlSur.add(pnlBotonVolver, BorderLayout.SOUTH);

        // --- Split entre centro y sur ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlCentro, pnlSur);
        splitPane.setDividerLocation(160);
        splitPane.setResizeWeight(0.35);
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.CENTER);

        // --- Acción botón información ---
        btnInformacion.addActionListener(e -> {
            try {
                id = Integer.parseInt(txtId.getText().trim());
                txtResultado.setText(recuperarInformacion());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce un ID numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
            }
        });

        // --- Acción botón volver ---
        btnVolver.addActionListener(e -> ventanaPrincipal.mostrarMenu());
    }

    // --- Recuperar información del servidor ---
    public String recuperarInformacion() {
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getCustomer";
        session.put("id", id);
        session = cliente.sentMessage(context, session);
        CampoGolf cu = (CampoGolf) session.get("Customer");
        if (cu == null) {
            return "Error - No encontrado en la base de datos";
        } else {
            return "Nombre: " + cu.getNombre() + "\n"
                    + "Ubicación: " + cu.getUbicacion() + "\n"
                    + "Fecha de inauguración: " + cu.getFecha_inauguracion() + "\n"
                    + "Número de hoyos: " + cu.getNum_hoyos() + "\n"
                    + "Par total: " + cu.getPar();
        }
    }
}


