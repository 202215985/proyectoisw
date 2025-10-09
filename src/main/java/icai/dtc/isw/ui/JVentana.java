

package icai.dtc.isw.ui;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class JVentana extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(JVentana::new);
    }

    private int id;

    public JVentana() {
        super("HoleIn1cai");
        this.setLayout(new BorderLayout());

        // --- Norte: Título ---
        JPanel pnlNorte = new JPanel(new BorderLayout());
        JLabel lblTitulo = new JLabel("Introduzca el id de campo (1-7)", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Courier", Font.BOLD, 20));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        pnlNorte.add(lblTitulo, BorderLayout.CENTER);
        this.add(pnlNorte, BorderLayout.NORTH);

        // --- Centro (input): label + textfield + botón ---
        JPanel pnlCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 16));
        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField();
        txtId.setColumns(10); // controla el ancho visible del campo
        JButton btnInformacion = new JButton("Recibir información");
        pnlCentro.add(lblId);
        pnlCentro.add(txtId);
        pnlCentro.add(btnInformacion);

        // --- Sur (resultado): label + text area con scroll ---
        JPanel pnlSur = new JPanel(new BorderLayout(8, 8));
        JLabel lblResultado = new JLabel("El resultado obtenido es:");
        JTextArea txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setLineWrap(true);
        txtResultado.setWrapStyleWord(true);
        JScrollPane scrollResultado = new JScrollPane(txtResultado,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Márgenes y composición
        pnlSur.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));
        pnlSur.add(lblResultado, BorderLayout.NORTH);
        pnlSur.add(scrollResultado, BorderLayout.CENTER);

        // --- SplitPane para controlar proporciones entre input (arriba) y resultado (abajo) ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pnlCentro, pnlSur);
        splitPane.setDividerLocation(160);   // altura del panel superior
        splitPane.setResizeWeight(0.35);     // ~35% arriba, ~65% abajo
        splitPane.setOneTouchExpandable(true);
        this.add(splitPane, BorderLayout.CENTER);

        // --- Acción botón ---
        btnInformacion.addActionListener(e -> {
            try {
                id = Integer.parseInt(txtId.getText().trim());
                txtResultado.setText(recuperarInformacion());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce un ID numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
            }
        });

        // --- Ventana ---
        this.setSize(900, 520);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public String recuperarInformacion() {
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getCustomer";
        session.put("id", id);
        session = cliente.sentMessage(context, session);
        Customer cu = (Customer) session.get("Customer");
        if (cu == null) {
            return "Error - No encontrado en la base de datos";
        } else {
            return "Nombre: " + cu.getNombre() + ",Ubicación: " + cu.getUbicacion() + ",Fecha de inauguracion: " + cu.getFecha_inauguracion()
                    + ",Numero de hoyos: " + cu.getNum_hoyos() + ",Par total: " + cu.getPar();
        }
    }
}

