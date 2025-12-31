
package icai.dtc.isw.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.CampoGolf;

public class JVentanaCampos extends JFondo {

    private boolean opcionInicialRemovida = false;
    private ArrayList<CampoGolf> listaCampos;

    public JVentanaCampos(JSesion ventanaPrincipal) {
        super("/fondo.jpg");
        setLayout(new BorderLayout());

        // --- Norte: título ---
        JPanel pnlNorte = new JPanel(new BorderLayout());
        pnlNorte.setOpaque(false);
        JLabel lblTitulo = new JLabel("Campos adheridos a nuestra plataforma", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Courier", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        pnlNorte.add(lblTitulo, BorderLayout.CENTER);
        this.add(pnlNorte, BorderLayout.NORTH);

        // --- Centro: combo + botón ---
        JPanel pnlCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 16));
        pnlCentro.setOpaque(false);
        JLabel lblCampo = new JLabel("Campo:");
        JComboBox<CampoGolf> comboCampos = new JComboBox<>();
        // Agregar opción inicial
        CampoGolf opcionInicial = new CampoGolf(-1, "Seleccione un campo", "", 0, "", 0);
        comboCampos.addItem(opcionInicial);
        ArrayList<CampoGolf> campos = getCampos();
        listaCampos = new ArrayList<>(campos);
        for (CampoGolf campo : campos) {
            comboCampos.addItem(campo);
        }

        // ActionListener para remover la opción inicial la primera vez que se selecciona un campo válido
        comboCampos.addActionListener(e -> {
            if (!opcionInicialRemovida && comboCampos.getSelectedIndex() > 0) {
                comboCampos.removeItemAt(0);
                opcionInicialRemovida = true;
            }
        });
        JButton btnInformacion = new JButton("Mostrar información");
        btnInformacion.setOpaque(false);
        btnInformacion.setBackground(new java.awt.Color(0, 0, 0, 0)); // Transparente
        pnlCentro.add(lblCampo);
        pnlCentro.add(comboCampos);
        pnlCentro.add(btnInformacion);

        // --- Sur: resultado + botón volver ---
        JPanel pnlSur = new JPanel(new BorderLayout(8, 8));
        JLabel lblResultado = new JLabel("Información del campo elegido:");
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
            CampoGolf campoSeleccionado = (CampoGolf) comboCampos.getSelectedItem();
            if (campoSeleccionado != null && campoSeleccionado.getId_campo() != -1) {
                txtResultado.setText(mostrarInformacion(campoSeleccionado));
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un campo válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Acción botón volver ---
        btnVolver.addActionListener(e -> {
            // Resetear controles a estado inicial
            comboCampos.removeAllItems();
            CampoGolf resetOpcion = new CampoGolf(-1, "Seleccione un campo", "", 0, "", 0);
            comboCampos.addItem(resetOpcion);
            for (CampoGolf campo : listaCampos) {
                comboCampos.addItem(campo);
            }
            comboCampos.setSelectedIndex(0);
            opcionInicialRemovida = false;
            txtResultado.setText("");
            ventanaPrincipal.mostrarMenu();
        });
    }

    // --- Mostrar información del campo seleccionado ---
    public String mostrarInformacion(CampoGolf campo) {
        if (campo == null) {
            return "Error - Campo no encontrado";
        } else {
            return "Nombre: " + campo.getNombre() + "\n"
                    + "Ubicación: " + campo.getUbicacion() + "\n"
                    + "Fecha de inauguración: " + campo.getFecha_inauguracion() + "\n"
                    + "Número de hoyos: " + campo.getNum_hoyos() + "\n"
                    + "Par total: " + campo.getPar();
        }
    }

    // --- Obtener lista de campos ---
    public ArrayList<CampoGolf> getCampos(){
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getCampos";
        session = cliente.sentMessage(context, session);
        @SuppressWarnings("unchecked")
        ArrayList<CampoGolf> campos = (ArrayList<CampoGolf>) session.get("Campos");
        if (campos == null) {
            System.out.println("Advertencia: Lista de campos null en respuesta del servidor");
            return new ArrayList<>();
        }
        return campos;
    }
}


