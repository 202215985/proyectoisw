package icai.dtc.isw.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CamposGolfPanel extends JPanel {
   
    private JComboBox<CampoGolf> comboCampos;
    private JLabel labelCampo;
    private JPanel panelInfo;
    private JLabel labelNombre;
    private JLabel labelNumHoyos;
    private JLabel labelId;
    private JPanel panelHoyos;
    private JLabel labelTotalGolpes;
    private List<JTextField> textFieldsHoyos;
   
    // Clase interna para representar un campo de golf
    public static class CampoGolf {
        private int idCampo;
        private String nombreCampo;
        private int numHoyos;
       
        public CampoGolf(int idCampo, String nombreCampo, int numHoyos) {
            this.idCampo = idCampo;
            this.nombreCampo = nombreCampo;
            this.numHoyos = numHoyos;
        }
       
        public int getIdCampo() {
            return idCampo;
        }
       
        public String getNombreCampo() {
            return nombreCampo;
        }
       
        public int getNumHoyos() {
            return numHoyos;
        }
       
        @Override
        public String toString() {
            return nombreCampo + " (" + numHoyos + " hoyos)";
        }
    }
   
    public CamposGolfPanel() {
        textFieldsHoyos = new ArrayList<>();
        initComponents();
        cargarCampos();
        configurarEventos();
    }
   
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
       
        // Panel superior con el combo
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        labelCampo = new JLabel("Seleccionar Campo:");
        comboCampos = new JComboBox<>();
        comboCampos.setPreferredSize(new Dimension(300, 25));
       
        panelSuperior.add(labelCampo);
        panelSuperior.add(comboCampos);
       
        // Panel de información del campo
        panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información del Campo"));
       
        labelId = new JLabel("ID: -");
        labelNombre = new JLabel("Nombre: -");
        labelNumHoyos = new JLabel("Número de Hoyos: -");
       
        Font font = new Font("Arial", Font.PLAIN, 14);
        labelId.setFont(font);
        labelNombre.setFont(font);
        labelNumHoyos.setFont(font);
       
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(labelId);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(labelNombre);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(labelNumHoyos);
        panelInfo.add(Box.createVerticalStrut(10));
       
        panelInfo.setVisible(false);
       
        // Panel para los hoyos con scroll
        panelHoyos = new JPanel();
        panelHoyos.setLayout(new GridBagLayout());
        panelHoyos.setBorder(BorderFactory.createTitledBorder("Golpes por Hoyo"));
       
        JScrollPane scrollPaneHoyos = new JScrollPane(panelHoyos);
        scrollPaneHoyos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneHoyos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneHoyos.setPreferredSize(new Dimension(600, 300));
       
        // Panel inferior con el total
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        labelTotalGolpes = new JLabel("Total de Golpes: 0");
        labelTotalGolpes.setFont(new Font("Arial", Font.BOLD, 16));
        labelTotalGolpes.setForeground(new Color(0, 100, 0));
        panelInferior.add(labelTotalGolpes);
       
        // Panel central que contiene info y hoyos
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.add(panelInfo, BorderLayout.NORTH);
        panelCentral.add(scrollPaneHoyos, BorderLayout.CENTER);
        panelCentral.add(panelInferior, BorderLayout.SOUTH);
       
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
    }
   
    private void configurarEventos() {
        comboCampos.addActionListener(e -> {
            CampoGolf campoSeleccionado = (CampoGolf) comboCampos.getSelectedItem();
            mostrarInformacionCampo(campoSeleccionado);
        });
    }
   
    private void mostrarInformacionCampo(CampoGolf campo) {
        if (campo != null) {
            labelId.setText("ID: " + campo.getIdCampo());
            labelNombre.setText("Nombre: " + campo.getNombreCampo());
            labelNumHoyos.setText("Número de Hoyos: " + campo.getNumHoyos());
            panelInfo.setVisible(true);
           
            // Crear los textfields para los hoyos
            crearTextFieldsHoyos(campo.getNumHoyos());
        } else {
            panelInfo.setVisible(false);
            limpiarHoyos();
        }
    }
   
    private void crearTextFieldsHoyos(int numHoyos) {
        // Limpiar panel y lista anterior
        panelHoyos.removeAll();
        textFieldsHoyos.clear();
       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
       
        // Crear un textfield por cada hoyo
        for (int i = 1; i <= numHoyos; i++) {
            gbc.gridx = (i - 1) % 6; // 6 columnas
            gbc.gridy = (i - 1) / 6;
           
            JPanel panelHoyo = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
           
            JLabel labelHoyo = new JLabel("Hoyo " + i + ":");
            labelHoyo.setPreferredSize(new Dimension(60, 20));
           
            JTextField textField = new JTextField(3);
            textField.setHorizontalAlignment(JTextField.CENTER);
           
            // Añadir FocusListener para actualizar el total al perder el foco
            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    actualizarTotalGolpes();
                }
            });
           
            textFieldsHoyos.add(textField);
           
            panelHoyo.add(labelHoyo);
            panelHoyo.add(textField);
           
            panelHoyos.add(panelHoyo, gbc);
        }
       
        // Actualizar la interfaz
        panelHoyos.revalidate();
        panelHoyos.repaint();
       
        // Resetear el total
        labelTotalGolpes.setText("Total de Golpes: 0");
    }
   
    private void actualizarTotalGolpes() {
        int total = 0;
       
        for (JTextField textField : textFieldsHoyos) {
            String texto = textField.getText().trim();
            if (!texto.isEmpty()) {
                try {
                    int golpes = Integer.parseInt(texto);
                    if (golpes > 0) {
                        total += golpes;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar valores no numéricos
                }
            }
        }
       
        labelTotalGolpes.setText("Total de Golpes: " + total);
    }
   
    private void limpiarHoyos() {
        panelHoyos.removeAll();
        textFieldsHoyos.clear();
        panelHoyos.revalidate();
        panelHoyos.repaint();
        labelTotalGolpes.setText("Total de Golpes: 0");
    }
   
    private void cargarCampos() {
        List<CampoGolf> campos = obtenerCamposDesdeDB();
       
        comboCampos.removeAllItems();
        comboCampos.addItem(null);
       
        for (CampoGolf campo : campos) {
            comboCampos.addItem(campo);
        }
    }
   
    private List<CampoGolf> obtenerCamposDesdeDB() {
        List<CampoGolf> campos = new ArrayList<>();
       
        String url = "jdbc:postgresql://localhost:5432/tu_base_de_datos";
        String usuario = "tu_usuario";
        String password = "tu_password";
       
        String query = "SELECT id_campo, nombre_campo, num_hoyos FROM campos ORDER BY nombre_campo";
       
        try (Connection conn = DriverManager.getConnection(url, usuario, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
           
            while (rs.next()) {
                int idCampo = rs.getInt("id_campo");
                String nombreCampo = rs.getString("nombre_campo");
                int numHoyos = rs.getInt("num_hoyos");
               
                campos.add(new CampoGolf(idCampo, nombreCampo, numHoyos));
            }
           
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error al cargar los campos: " + e.getMessage(),
                "Error de Base de Datos",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
       
        return campos;
    }
   
    public CampoGolf getCampoSeleccionado() {
        return (CampoGolf) comboCampos.getSelectedItem();
    }
   
    public void recargarCampos() {
        cargarCampos();
    }
   
    // Método para obtener los golpes de cada hoyo
    public List<Integer> getGolpesPorHoyo() {
        List<Integer> golpes = new ArrayList<>();
        for (JTextField textField : textFieldsHoyos) {
            String texto = textField.getText().trim();
            if (!texto.isEmpty()) {
                try {
                    golpes.add(Integer.parseInt(texto));
                } catch (NumberFormatException e) {
                    golpes.add(0);
                }
            } else {
                golpes.add(0);
            }
        }
        return golpes;
    }
   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Campos de Golf - Registro de Golpes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
           
            CamposGolfPanel panel = new CamposGolfPanel();
            frame.add(panel);
           
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
