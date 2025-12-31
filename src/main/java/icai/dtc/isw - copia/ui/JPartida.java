package icai.dtc.isw.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import icai.dtc.isw.client.Client;
import icai.dtc.isw.domain.CampoGolf;
import icai.dtc.isw.domain.Hoyo;
import icai.dtc.isw.domain.Partida;
import icai.dtc.isw.domain.ResultadoPartida;
import icai.dtc.isw.domain.Usuario;

public class JPartida extends JPanel {

    private JSesion ventanaPrincipal;
    private Usuario usu;
    private JComboBox<CampoGolf> comboCampos;
    private JTextField txtResultado;
    private JTextField txtPar;
    private JButton btnGuardar;
    private JButton btnVolver;
    private CampoGolf campoActual;
    private JPanel panelHoyos;
    private JPanel panelInferior;
    private CampoGolf campoSeleccionado;
    private ArrayList<Hoyo> hoyos;
    private ArrayList<JTextField> num_golpes;

    public JPartida(JSesion ventanaPrincipal, Usuario usu) {
        this.ventanaPrincipal = ventanaPrincipal;
        this.usu = usu;
        
        hoyos = new ArrayList<>();
        num_golpes = new ArrayList<>();

        setLayout(new GridBagLayout());
        setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0;

        // ===== COMBO CAMPOS =====
        comboCampos = new JComboBox<>();
        ArrayList<CampoGolf> campos = getCampos();
        for (CampoGolf campo : campos) {
            comboCampos.addItem(campo);
        }

        comboCampos.addActionListener(e -> {
            campoSelec();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(comboCampos, gbc);

        // ===== PANEL HOYOS =====
        panelHoyos = new JPanel(new GridBagLayout());
        panelHoyos.setOpaque(false);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        add(panelHoyos, gbc);

        // ===== PANEL INFERIOR =====
        panelInferior = new JPanel(new GridBagLayout());
        panelInferior.setOpaque(false);

        gbc.gridy = 2;
        gbc.weighty = 0;
        add(panelInferior, gbc);

        // ===== CAMPOS INFERIORES =====
        txtResultado = new JTextField(5);
        txtResultado.setEditable(false);

        txtPar = new JTextField(5);
        txtPar.setEditable(false);

        btnGuardar = new JButton("Guardar partida");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(e -> {
            int resultado = Integer.parseInt(txtResultado.getText());
            int par = campoActual.getPar();
            int diferencia = resultado - par;

            String textoDiferencia;
            if (diferencia > 0) {
                textoDiferencia = "+" + diferencia;
            } else {
                textoDiferencia = String.valueOf(diferencia);
            }

            String mensaje =
                    "Resumen de la partida\n\n" +
                    "Golpes totales: " + resultado + "\n" +
                    "Par del campo: " + par + "\n" +
                    "Resultado final: " + textoDiferencia;

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    mensaje,
                    "Confirmar partida",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (opcion == JOptionPane.OK_OPTION) {
                java.sql.Date fecha = java.sql.Date.valueOf(java.time.LocalDate.now());
                Partida partida = new Partida(usu.getDni(), fecha, resultado);
                partida=setPartida(partida);
                System.out.println(partida.getIdPartida() + "id partida");
                for (int i = 0; i < hoyos.size(); i++) {
                    ResultadoPartida resul = new ResultadoPartida(hoyos.get(i).getIdHoyo(), partida.getIdPartida(), Integer.parseInt(num_golpes.get(i).getText()));
                    setResultado(resul);
                    ventanaPrincipal.mostrarPerfil(usu);
                }
                System.out.println("Partida de " + partida.getDniUsuario() + "con id_partida: " + partida.getIdPartida() + " confirmada");
            }
        });

        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(e -> ventanaPrincipal.mostrarPerfil(usu));
    }

    private void campoSelec() {
        campoSeleccionado = (CampoGolf) comboCampos.getSelectedItem();
        if (campoSeleccionado != null) {
            cargarHoyos(campoSeleccionado.getId_campo());
        }
    }

    public ArrayList<Hoyo> getHoyos(int id_campo){
        Client cliente;
        cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getHoyos";
        session.put("id_campo", id_campo);
        session = cliente.sentMessage(context, session);
        ArrayList<Hoyo> hoyos = (ArrayList<Hoyo>) session.get("hoyos");   
        return hoyos;
    }

    public Partida setPartida(Partida p){
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/setPartida";
        session.put("Partida", p);
        session = cliente.sentMessage(context, session);
        Partida partida = (Partida) session.get("Partida");
        System.out.println("id partida" + partida.getIdPartida());
        return partida;
    }

    public ResultadoPartida setResultado(ResultadoPartida r){
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/setResultado";
        session.put("Resultado", r);
        session = cliente.sentMessage(context, session);
        ResultadoPartida resul = (ResultadoPartida) session.get("Resultado");
        System.out.println(resul.getIdHoyo() + " 3132131");
        return resul;
    }

    public ArrayList<CampoGolf> getCampos(){
        Client cliente = new Client();
        HashMap<String, Object> session = new HashMap<>();
        String context = "/getCampos";
        session = cliente.sentMessage(context, session);
        ArrayList<CampoGolf> campos = (ArrayList<CampoGolf>) session.get("Campos");   
        return campos;
    }

    private void cargarHoyos(int idCampo) {

        panelHoyos.removeAll();
        panelInferior.removeAll();
        num_golpes.clear();

        txtResultado.setText("");
        btnGuardar.setEnabled(false);

        campoActual = (CampoGolf) comboCampos.getSelectedItem();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        hoyos = getHoyos(idCampo);
        num_golpes = new ArrayList<JTextField>();

        int filaIzq = 0;
        int filaDer = 0;

        for (int i = 0; i < hoyos.size(); i++) {

            boolean derecha = i >= 18;
            int baseX = derecha ? 2 : 0;
            int fila = derecha ? filaDer : filaIzq;

            JLabel lblHoyo = new JLabel("Hoyo " + (i + 1));
            JTextField txtGolpes = new JTextField(4);
            

            ((AbstractDocument) txtGolpes.getDocument())
                    .setDocumentFilter(new NumericFilter());

            txtGolpes.getDocument().addDocumentListener(new DocumentListener() {
                @Override public void insertUpdate(DocumentEvent e) { actualizarResultado(); }
                @Override public void removeUpdate(DocumentEvent e) { actualizarResultado(); }
                @Override public void changedUpdate(DocumentEvent e) {}
            });

            gbc.gridx = baseX;
            gbc.gridy = fila;
            panelHoyos.add(lblHoyo, gbc);

            gbc.gridx = baseX + 1;
            panelHoyos.add(txtGolpes, gbc);

            num_golpes.add(txtGolpes);

            if (derecha) filaDer++; else filaIzq++;
        }

        // ===== PANEL INFERIOR =====
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelInferior.add(new JLabel("Resultado:"), gbc);

        gbc.gridx = 1;
        panelInferior.add(txtResultado, gbc);

        gbc.gridx = 2;
        panelInferior.add(new JLabel("Par:"), gbc);

        gbc.gridx = 3;
        txtPar.setText(String.valueOf(campoActual.getPar()));
        panelInferior.add(txtPar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelInferior.add(btnGuardar, gbc);

        gbc.gridx = 2;
        gbc.gridwidth = 2;
        panelInferior.add(btnVolver, gbc);

        panelHoyos.revalidate();
        panelHoyos.repaint();
        panelInferior.revalidate();
        panelInferior.repaint();
    }

    private void actualizarResultado() {

        int suma = 0;
        boolean todosRellenos = true;

        for (JTextField tf : num_golpes) {
            String texto = tf.getText();

            if (texto.isEmpty()) {
                todosRellenos = false;
                continue;
            }

            try {
                int valor = Integer.parseInt(texto);
                suma += valor;
            } catch (NumberFormatException e) {
                todosRellenos = false;
            }
        }

        txtResultado.setText(String.valueOf(suma));
        btnGuardar.setEnabled(todosRellenos);
    }

    class NumericFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string.matches("\\d+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text.matches("\\d*")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}


