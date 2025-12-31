package icai.dtc.isw.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TablaDinamica extends JFrame {
    private JTextField txtFilas;
    private JTextField txtColumnas;
    private JButton btnCrearTabla;
    private JButton btnObtenerDatos;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;

    public TablaDinamica() {
        setTitle("Editor de Tabla Dinámica");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior con controles
        JPanel panelSuperior = new JPanel(new FlowLayout());
        
        panelSuperior.add(new JLabel("Filas:"));
        txtFilas = new JTextField("5", 5);
        panelSuperior.add(txtFilas);
        
        panelSuperior.add(new JLabel("Columnas:"));
        txtColumnas = new JTextField("4", 5);
        panelSuperior.add(txtColumnas);
        
        btnCrearTabla = new JButton("Crear Tabla");
        panelSuperior.add(btnCrearTabla);
        
        btnObtenerDatos = new JButton("Obtener Datos");
        panelSuperior.add(btnObtenerDatos);
        
        add(panelSuperior, BorderLayout.NORTH);

        // Acciones de los botones
        btnCrearTabla.addActionListener(e -> crearTabla());
        btnObtenerDatos.addActionListener(e -> obtenerDatos());

        setLocationRelativeTo(null);
    }

    private void crearTabla() {
        try {
            int numFilas = Integer.parseInt(txtFilas.getText());
            int numColumnas = Integer.parseInt(txtColumnas.getText());

            if (numFilas <= 0 || numColumnas <= 1) {
                JOptionPane.showMessageDialog(this, 
                    "Debe haber al menos 1 fila y 2 columnas", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nombres de columnas
            String[] nombresColumnas = new String[numColumnas];
            nombresColumnas[0] = "Nº Fila";
            for (int i = 1; i < numColumnas; i++) {
                nombresColumnas[i] = "Columna " + i;
            }

            // Crear modelo de tabla con control de edición
            modelo = new DefaultTableModel(nombresColumnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Solo editable a partir de la columna 1 (segunda columna)
                    return column > 0;
                }
            };

            // Llenar la tabla con filas
            for (int i = 0; i < numFilas; i++) {
                Object[] fila = new Object[numColumnas];
                fila[0] = i + 1; // Número de fila (primera columna)
                for (int j = 1; j < numColumnas; j++) {
                    fila[j] = ""; // Columnas editables vacías
                }
                modelo.addRow(fila);
            }

            // Crear o actualizar la tabla
            if (tabla == null) {
                tabla = new JTable(modelo);
                scrollPane = new JScrollPane(tabla);
                add(scrollPane, BorderLayout.CENTER);
            } else {
                tabla.setModel(modelo);
            }

            revalidate();
            repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese números válidos", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void obtenerDatos() {
        if (modelo == null || tabla == null) {
            JOptionPane.showMessageDialog(this, 
                "Primero debe crear una tabla", 
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numFilas = modelo.getRowCount();
        int numColumnas = modelo.getColumnCount();

        // Crear array para almacenar datos (excluye la primera columna)
        String[][] datos = new String[numFilas][numColumnas - 1];

        // Obtener datos a partir de la segunda columna
        for (int i = 0; i < numFilas; i++) {
            for (int j = 1; j < numColumnas; j++) {
                Object valor = modelo.getValueAt(i, j);
                datos[i][j - 1] = (valor != null) ? valor.toString() : "";
            }
        }

        // Mostrar datos por consola
        System.out.println("===== DATOS DE LA TABLA =====");
        for (int i = 0; i < datos.length; i++) {
            System.out.print("Fila " + (i + 1) + ": [");
            for (int j = 0; j < datos[i].length; j++) {
                System.out.print("\"" + datos[i][j] + "\"");
                if (j < datos[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
        System.out.println("============================\n");

        JOptionPane.showMessageDialog(this, 
            "Datos mostrados en la consola", 
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TablaDinamica ventana = new TablaDinamica();
            ventana.setVisible(true);
        });
    }
}