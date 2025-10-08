package icai.dtc.isw.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.Customer;

public class JVentana extends JFrame {
    public static void main(String[] args) {
        new JVentana();
    }
    private int id;
    private final JPanel pnlCentro = new JPanel();
    private final ArrayList<String> campos = new ArrayList<>();

    public JVentana() {
        super("INGENIERÍA DEL SOFTWARE");
        this.setLayout(new BorderLayout());
        //Pongo un panel arriba con el título
        JPanel pnlNorte = new JPanel();
        JLabel lblTitulo = new JLabel("Listado Campos de Golf", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Courier", Font.BOLD, 20));
        pnlNorte.add(lblTitulo);
        this.add(pnlNorte, BorderLayout.NORTH);

        //Pongo el panel central el botón
        campos.add("Real Club de Golf La Herrería");
        campos.add("Real Club La Moraleja");
        campos.add("Centro Nacional de Golf");
        campos.add("Olivar de la Hinojosa");
        campos.add("Club de Golf Retamares");
        campos.add("Club de Campo Villa de Madrid");

        // Panel
        pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.Y_AXIS));
        add(pnlCentro, BorderLayout.CENTER);

        // Pinta la lista inicial
        pintarLista();

        this.setSize(550,500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
    }

    /** Dibuja los botones (uno por campo) */
    private void pintarLista() {
        pnlCentro.removeAll();
        pnlCentro.add(Box.createVerticalStrut(20));

        for (int i = 0; i < campos.size(); i++) {
            String campo = campos.get(i);

            JButton boton = new JButton(campo);
            boton.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Solo mostramos la FRASE (segundo String), sin título
            boton.addActionListener(e -> {
                switch (campo) {
                    case "Real Club de Golf La Herrería":
                        mostrarInfo("Ubicado en San Lorenzo de El Escorial, entorno natural privilegiado.");
                        break;
                    case "Real Club La Moraleja":
                        mostrarInfo("4 recorridos de 18 hoyos, diseño de Jack Nicklaus.");
                        break;
                    case "Centro Nacional de Golf":
                        mostrarInfo("Instalación de la RFEG con acceso público (18 hoyos).");
                        break;
                    case "Olivar de la Hinojosa":
                        mostrarInfo("18 hoyos par 72, junto a IFEMA.");
                        break;
                    case "Club de Golf Retamares":
                        mostrarInfo("18 hoyos diseñados por José María Olazábal.");
                        break;
                    case "Club de Campo Villa de Madrid":
                        mostrarInfo("Sede habitual del Open de España.");
                        break;
                    default:
                        mostrarInfo("Campo no reconocido.");
                }
            });

            pnlCentro.add(boton);
            if (i < campos.size() - 1) {
                pnlCentro.add(Box.createVerticalStrut(15));
            }
        }

        pnlCentro.revalidate();
        pnlCentro.repaint();
    }

    /** Muestra solo la frase + botón Volver que reconstruye la lista */
    private void mostrarInfo(String frase) {
        pnlCentro.removeAll();

        JLabel lbl = new JLabel(frase, SwingConstants.CENTER);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnVolver = new JButton("Volver");
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> pintarLista());

        pnlCentro.add(Box.createVerticalStrut(30));
        pnlCentro.add(lbl);
        pnlCentro.add(Box.createVerticalStrut(20));
        pnlCentro.add(btnVolver);

        pnlCentro.revalidate();
        pnlCentro.repaint();

    }
        // JPanel pnlCentro = new JPanel();
        // pnlCentro.add(Box.createVerticalStrut(20));
        // ArrayList<String> campos = new ArrayList<>();
        // campos.add("Real Club de Golf La Herrería");
        // campos.add("Real Club La Moraleja");
        // campos.add("Centro Nacional de Golf");
        // campos.add("Olivar de la Hinojosa");
        // campos.add("Club de Golf Retamares");
        // campos.add("Club de Campo Villa de Madrid");

        // // Añado automaticamente botones al jpanel y en caso de pulsar el boton enseño info
        // for (int i = 0; i < campos.size(); i++) {
        //     String campo = campos.get(i);

        //     JButton boton = new JButton(campo);
        //     boton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //     // boton.addActionListener(e -> {
        //     //     switch (campo) {
        //     //         case "Real Club de Golf La Herrería" ->
        //     //             mostrarInfo("La Herrería", "Ubicado en San Lorenzo de El Escorial, entorno natural privilegiado.");
        //     //         case "Real Club La Moraleja" ->
        //     //             mostrarInfo("La Moraleja", "Club emblemático con 4 recorridos, diseño de Jack Nicklaus.");
        //     //         case "Centro Nacional de Golf" ->
        //     //             mostrarInfo("Centro Nacional", "Instalación de la RFEG, accesible y muy completa (18 hoyos).");
        //     //         case "Olivar de la Hinojosa" ->
        //     //             mostrarInfo("Olivar de la Hinojosa", "Junto a IFEMA; recorrido de 18 hoyos + 9 hoyos adicionales.");
        //     //         case "Club de Golf Retamares" ->
        //     //             mostrarInfo("Retamares", "Diseño de José María Olazábal; vistas al noreste de Madrid.");
        //     //         case "Club de Campo Villa de Madrid" ->
        //     //             mostrarInfo("Club de Campo", "Sede habitual de grandes torneos como el Open de España.");
        //     //         default ->
        //     //             mostrarInfo("Información", "Campo no reconocido.");
        //     //     }

        //     pnlCentro.add(boton);
        //     if (i < campos.size() - 1) {
        //         pnlCentro.add(Box.createVerticalStrut(15));
        //     }
        // }

        // pnlCentro.add(Box.createVerticalStrut(20));
        // JButton campo1 = new JButton("Real Club de Golf La Herrería");
        // JButton campo2 = new JButton("Real Club La Moraleja");
        // JButton campo3 = new JButton("Centro Nacional de Golf");
        // JButton campo4 = new JButton("Olivar de la Hinojosa");
        // JButton campo5 = new JButton("Club de Golf Retamares");
        // JButton campo6 = new JButton("Club de Campo Villa de Madrid");
        // campo1.setAlignmentX(Component.CENTER_ALIGNMENT);
        // campo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        // campo3.setAlignmentX(Component.CENTER_ALIGNMENT);
        // campo4.setAlignmentX(Component.CENTER_ALIGNMENT);
        // campo5.setAlignmentX(Component.CENTER_ALIGNMENT);
        // campo6.setAlignmentX(Component.CENTER_ALIGNMENT);
        // pnlCentro.add(campo1);
        // pnlCentro.add(Box.createVerticalStrut(10));
        // pnlCentro.add(campo2);
        // pnlCentro.add(Box.createVerticalStrut(10));
        // pnlCentro.add(campo3);
        // pnlCentro.add(Box.createVerticalStrut(10));
        // pnlCentro.add(campo4);
        // pnlCentro.add(Box.createVerticalStrut(10));
        // pnlCentro.add(campo5);
        // pnlCentro.add(Box.createVerticalStrut(10));
        // pnlCentro.add(campo6); 
        // pnlCentro.setLayout(new BoxLayout(pnlCentro, BoxLayout.	Y_AXIS));
        // this.add(pnlCentro, BorderLayout.CENTER);

        // //Añado el listener al botón
        // /* campo1.addActionListener(actionEvent -> {
        //     id=Integer.parseInt(txtId.getText());
        //     txtResultado.setText(recuperarInformacion());
        // }); */

        // this.setSize(550,500);
        // this.setResizable(false);
        // this.setLocationRelativeTo(null);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setVisible(true);
    

    public String recuperarInformacion() {
        Client cliente=new Client();
        HashMap<String,Object> session=new HashMap<>();
        String context="/getCustomer";
        session.put("id",id);
        session=cliente.sentMessage(context,session);
        Customer cu=(Customer)session.get("Customer");
        String nombre;
        if (cu==null) {
            nombre="Error - No encontrado en la base de datos";
        }else {
            nombre=cu.getName();
        }
        return nombre;
    }
}
