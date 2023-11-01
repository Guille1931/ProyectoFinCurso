package pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Source;

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
                String url = searchField.getText();

                // Intenta cargar la URL y mostrarla correctamente en el JEditorPane
                try {
                    URL urlObject = new URL(url);
                    Source source = new Source(urlObject);
                    Renderer renderer = new Renderer(source);
                    editorPane.setText(renderer.toString());
                } catch (IOException ex) {
                    editorPane.setText("Error al cargar la página.");
                }

                // Realiza alguna acción con la consulta, por ejemplo, cargar una página de búsqueda.
                // Aquí puedes implementar la lógica de búsqueda.

                // Guarda la URL en la base de datos
                guardarURLenBaseDeDatos(url);

                System.out.println("Búsqueda: " + url);
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

    private static void guardarURLenBaseDeDatos(String url) {
        // Aquí iría el código para guardar la URL en la base de datos (igual que antes)
    }
}
