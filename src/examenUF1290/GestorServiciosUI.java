package examenUF1290;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class GestorServiciosUI {
    private JFrame frame;
    private ServicioManager servicioManager;
    private JButton guardarButton;
    private JButton mostrarButton;
    private JPanel panel;
    private JFrame secundaria;
    private JTextField fechaField;
    private JTextField conceptoField;
    private JComboBox<String> tecnicoComboBox;
    private JComboBox<String> categoriaComboBox;
    private JCheckBox terminadoCheckBox;
    private JRadioButton urgenteRadioButton;
    private JRadioButton normalRadioButton;
    private JRadioButton pospuestoRadioButton;
    private JLabel resultadoLabel;
    private JPanel panelSecundario;
    private Servicio servicio;
    private String fecha;
    private String concepto;
    private String tecnico;
    private String categoria;
    private ButtonGroup grupoRadios;
    private StringBuilder serviciosTexto;
    private JLabel label;
    private JLabel label_1;
    private JLabel label_2;
    private JLabel label_3;
    private boolean terminado;
    private List<Servicio> serviciosMostrados;
    private int countUrgentes;
    private int countNormales;
    private int countPospuestos;
    
    // Constructor de la interfaz de usuario
    public GestorServiciosUI() {
        // Configuración de la ventana principal
        frame = new JFrame("Gestor de Servicios");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Inicialización del gestor de servicios
        servicioManager = new ServicioManager();

        // Configuración de componentes de la interfaz
        configurarComponentes();

        // Mostrar la ventana principal
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    // Configuración de componentes de la interfaz
    private void configurarComponentes() {
        // Botones
        guardarButton = new JButton("Guardar Servicio");
        guardarButton.setBounds(10, 127, 111, 23);
        mostrarButton = new JButton("Mostrar Servicios");
        mostrarButton.setBounds(259, 127, 115, 23);

        // Campos de texto y selección
        fechaField = new JTextField(10);
        fechaField.setBounds(64, 5, 94, 20);
        conceptoField = new JTextField(10);
        conceptoField.setBounds(230, 5, 133, 20);
        tecnicoComboBox = new JComboBox<>(new String[]{"Tecnico1", "Tecnico2", "Tecnico3"});
        tecnicoComboBox.setBounds(64, 54, 94, 20);
        categoriaComboBox = new JComboBox<>(new String[]{"Urgente", "Normal", "Pospuesto"});
        categoriaComboBox.setBounds(275, 54, 76, 20);
        terminadoCheckBox = new JCheckBox("Terminado");
        terminadoCheckBox.setBounds(163, 127, 75, 23);

        // Acciones de los botones
        configurarAccionesBotones();

        // Configuración del panel principal
        configurarPanel();
    }

    // Configuración de las acciones de los botones
    private void configurarAccionesBotones() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener datos del servicio desde la interfaz
                fecha = fechaField.getText();
                concepto = conceptoField.getText();
                tecnico = (String) tecnicoComboBox.getSelectedItem();
                categoria = (String) categoriaComboBox.getSelectedItem();
                terminado = terminadoCheckBox.isSelected();

                // Crear un nuevo servicio
                servicio = new Servicio(fecha, concepto, tecnico, categoria);
                servicio.setTerminado(terminado);

                // Agregar el servicio al gestor de servicios
                servicioManager.agregarServicio(servicio);

                // Guardar en fichero
                guardarEnFichero(servicioManager);
            }
        });

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar la ventana secundaria con los servicios
                mostrarVentanaSecundaria(servicioManager);
            }
        });
    }

    // Guardar el gestor de servicios en un fichero
    private static void guardarEnFichero(ServicioManager servicioManager) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("servicios.txt"))) {
            out.writeObject(servicioManager);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Mostrar la ventana secundaria con los servicios
    private void mostrarVentanaSecundaria(ServicioManager servicioManager) {
        secundaria = new JFrame("Servicios Mostrados");
        secundaria.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        secundaria.setSize(400, 300);

        // Componentes de la ventana secundaria
        urgenteRadioButton = new JRadioButton("Urgente");
        normalRadioButton = new JRadioButton("Normal");
        pospuestoRadioButton = new JRadioButton("Pospuesto");

        // Agrupar los JRadioButton
        grupoRadios = new ButtonGroup();
        grupoRadios.add(urgenteRadioButton);
        grupoRadios.add(normalRadioButton);
        grupoRadios.add(pospuestoRadioButton);

        mostrarButton = new JButton("Mostrar");
        resultadoLabel = new JLabel();

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lista para almacenar los servicios que se mostrarán
                serviciosMostrados = null;

                // Contadores de servicios por tipo
                countUrgentes = 0;
                countNormales = 0;
                countPospuestos = 0;

                // Determinar qué tipo de servicios se deben mostrar
                if (urgenteRadioButton.isSelected()) {
                    serviciosMostrados = servicioManager.obtenerServiciosUrgentes();
                } else if (normalRadioButton.isSelected()) {
                    serviciosMostrados = servicioManager.obtenerServiciosNormales();
                } else if (pospuestoRadioButton.isSelected()) {
                    serviciosMostrados = servicioManager.obtenerServiciosPospuestos();
                }

                // Mostrar servicios en la interfaz
                if (serviciosMostrados != null) {
                    // Utilizar un StringBuilder para construir la cadena de texto
                    serviciosTexto = new StringBuilder();

                    // Iterar sobre la lista de servicios y agregar cada uno a la cadena
                    for (Servicio servicio : serviciosMostrados) {
                        serviciosTexto.append("Fecha: ").append(servicio.getFecha()).append("\n");
                        serviciosTexto.append("Concepto: ").append(servicio.getConcepto()).append("\n");
                        serviciosTexto.append("Técnico: ").append(servicio.getTecnico()).append("\n");
                        serviciosTexto.append("Categoría: ").append(servicio.getCategoria()).append("\n");
                        serviciosTexto.append("Terminado: ").append(servicio.isTerminado()).append("\n\n");

                        // Contar servicios por tipo
                        if ("Urgente".equals(servicio.getCategoria())) {
                            countUrgentes++;
                        } else if ("Normal".equals(servicio.getCategoria())) {
                            countNormales++;
                        } else if ("Pospuesto".equals(servicio.getCategoria())) {
                            countPospuestos++;
                        }
                    }

                    

                    // Mostrar contadores en la interfaz
                    resultadoLabel.setText(resultadoLabel.getText() +
                            "Total de servicios Urgentes: " + countUrgentes + "\n" +
                            "Total de servicios Normales: " + countNormales + "\n" +
                            "Total de servicios Pospuestos: " + countPospuestos);

                    // Repintar la ventana secundaria para reflejar los cambios
                    secundaria.getContentPane().removeAll();
                    configurarPanelSecundario();
                    secundaria.getContentPane().add(panelSecundario);
                    secundaria.repaint();
                }
            }
        });

        // Configuración del panel secundario
        configurarPanelSecundario();

        // Mostrar la ventana secundaria
        secundaria.getContentPane().add(panelSecundario);
        secundaria.setVisible(true);
    }

    // Configuración del panel principal
    private void configurarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        label = new JLabel("Fecha:");
        label.setBounds(10, 8, 33, 14);
        panel.add(label);
        panel.add(fechaField);
        label_1 = new JLabel("Concepto:");
        label_1.setBounds(170, 8, 50, 14);
        panel.add(label_1);
        panel.add(conceptoField);
        label_2 = new JLabel("Técnico:");
        label_2.setBounds(10, 57, 40, 14);
        panel.add(label_2);
        panel.add(tecnicoComboBox);
        label_3 = new JLabel("Categoría:");
        label_3.setBounds(214, 57, 51, 14);
        panel.add(label_3);
        panel.add(categoriaComboBox);
        panel.add(terminadoCheckBox);
        panel.add(guardarButton);
        panel.add(mostrarButton);
    }

    // Configuración del panel secundario
    private void configurarPanelSecundario() {
        panelSecundario = new JPanel();
        panelSecundario.add(urgenteRadioButton);
        panelSecundario.add(normalRadioButton);
        panelSecundario.add(pospuestoRadioButton);
        panelSecundario.add(mostrarButton);
        panelSecundario.add(resultadoLabel);
    }
}