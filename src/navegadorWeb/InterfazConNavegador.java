package navegadorWeb;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLEditorKit;

public class InterfazConNavegador {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazConNavegador::initAndShowGUI);
    }

    private static void initAndShowGUI() {
        JFrame frame = new JFrame("Interfaz con Navegador Web y Base de Datos");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        JLabel searchLabel = new JLabel("Buscar: ");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Buscar");
        JButton clearButton = new JButton("Limpiar");

        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setContentType("text/html; charset=UTF-8"); // Establecer la codificación UTF-8

        // Establecer el HTMLEditorKit y su StyleSheet si es necesario
        HTMLEditorKit editorKit = new HTMLEditorKit();
        editorPane.setEditorKit(editorKit);

        JScrollPane scrollPane = new JScrollPane(editorPane);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = searchField.getText();
                loadURL(editorPane, url);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                editorPane.setText("");
            }
        });

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        frame.add(searchPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private static void loadURL(JEditorPane editorPane, String url) {
        try {
            editorPane.setPage(new URL(url));
        } catch (IOException e) {
            editorPane.setText("Error al cargar la página: " + e.getMessage());
        }
    }
}

