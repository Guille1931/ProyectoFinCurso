package navegadorWeb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazConNavegador {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interfaz con Navegador Web y Base de Datos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel superior para el campo de búsqueda y botones
        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Buscar: ");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");
        JButton clearButton = new JButton("Limpiar");

        // Crea un JEditorPane para mostrar la página web
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html; charset=UTF-8");

        // Acción del botón de búsqueda
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        // Acción del botón de limpiar
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText(""); // Borra el texto del campo de búsqueda
                editorPane.setText(""); // Borra el contenido del JEditorPane al limpiar
            }
        });

        // Agrega componentes al panel de búsqueda
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        // Agrega componentes al JFrame
        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(editorPane), BorderLayout.CENTER);
        frame.setSize(800, 600); // Establece el tamaño del JFrame
        frame.setVisible(true);
    }
}
