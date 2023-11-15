package editorTexto;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorTexto extends JFrame {
    private static final long serialVersionUID = 1L;
    private JEditorPane editorPane; // Componente para editar el texto
    private File archivoAbierto; // Referencia al archivo abierto

    //Constructor de la clase
    public EditorTexto() {
    	// Establece el título de la ventana
        setTitle("Bloc de notas");
        // Establece el tamaño de la ventana
        setSize(450, 350);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // Ubica la ventana en la esquina inferior izquierda de la pantalla
        setLocation(0, getToolkit().getScreenSize().height - getHeight());
        // Barra de menú
        JMenuBar menuBar = new JMenuBar();
        // Asigna la barra de menú a la ventana
        setJMenuBar(menuBar);
        // Menú Archivo
        JMenu archivoMenu = new JMenu("Archivo");
        // Agrega el menú Archivo a la barra de menú
        menuBar.add(archivoMenu);

        // Elementos del menú Archivo
        JMenuItem nuevoItem = new JMenuItem("Nuevo");
        JMenuItem abrirItem = new JMenuItem("Abrir");
        JMenuItem guardarItem = new JMenuItem("Guardar");
        JMenuItem guardarComoItem = new JMenuItem("Guardar como");
        JMenuItem salirItem = new JMenuItem("Salir");

        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);

        // Menú Editar
        JMenu editarMenu = new JMenu("Editar");
        // Agrega el menú Editar a la barra de menú
        menuBar.add(editarMenu);

        // Elementos del menú Editar
        JMenuItem cortarItem = new JMenuItem("Cortar");
        JMenuItem copiarItem = new JMenuItem("Copiar");
        JMenuItem pegarItem = new JMenuItem("Pegar");

        editarMenu.add(cortarItem);
        editarMenu.add(copiarItem);
        editarMenu.add(pegarItem);

        // Componente para editar el texto
        editorPane = new JEditorPane();
        // Agrega barras de desplazamiento al editor
        JScrollPane scrollPane = new JScrollPane(editorPane);
        // Agrega el editor al centro de la ventana
        add(scrollPane, BorderLayout.CENTER);
        // ActionListener para el elemento "Nuevo" del menú
        nuevoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            	// Borra el contenido del editor
                editorPane.setText("");
                // Restablece el título
                setTitle("Bloc de notas");
                // No hay archivo abierto
                archivoAbierto = null;
            }
        });

        // ActionListener para el elemento "Abrir" del menú
        abrirItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            	// Selector de archivos
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
                // Establece un filtro para archivos .txt
                fileChooser.setFileFilter(filter);
                // Abre el cuadro de diálogo de apertura de archivo
                int resultado = fileChooser.showOpenDialog(EditorTexto.this);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                	// Obtiene el archivo seleccionado
                    archivoAbierto = fileChooser.getSelectedFile();
                    // Actualiza el título con el nombre del archivo
                    setTitle("Bloc de notas - " + archivoAbierto.getName());

                    try {
                    	// Lee el archivo
                        FileReader reader = new FileReader(archivoAbierto);
                        // Carga el contenido en el editor
                        editorPane.read(reader, null);
                        reader.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // ActionListener para el elemento "Guardar" del menú
        guardarItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                if (archivoAbierto != null) {
                    try {
                    	// Abre el archivo para escritura
                        FileWriter writer = new FileWriter(archivoAbierto);
                     // Guarda el contenido del editor en el archivo
                        editorPane.write(writer);
                        // Cierra el archivo
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                	// Si no hay archivo abierto, actúa como "Guardar como"
                    guardarComoItem.doClick();
                }
            }
        });

        // ActionListener para el elemento "Guardar como" del menú
        guardarComoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            	// Selector de archivos
            	JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
                // Establece un filtro para archivos .txt
                fileChooser.setFileFilter(filter);
                // Abre el cuadro de diálogo de guardado de archivo
                int resultado = fileChooser.showSaveDialog(EditorTexto.this);

                if (resultado == JFileChooser.APPROVE_OPTION) {
                	// Obtiene la ubicación y nombre del archivo
                    archivoAbierto = fileChooser.getSelectedFile();
                    // Actualiza el título con el nombre del archivo
                    setTitle("Bloc de notas - " + archivoAbierto.getName());
                    try {
                    	// Abre el archivo para escritura
                        FileWriter writer = new FileWriter(archivoAbierto);
                        // Guarda el contenido del editor en el archivo
                        editorPane.write(writer);
                        // Cierra el archivo
                        writer.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // ActionListener para el elemento "Salir" del menú
        salirItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra la aplicación
            }
        });

        // ActionListener para el elemento "Cortar" del menú
        cortarItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            	// Corta el texto seleccionado y lo coloca en el portapapeles
                editorPane.cut();
            }
        });

        // ActionListener para el elemento "Copiar" del menú
        copiarItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            	// Copia el texto seleccionado al portapapeles
            	editorPane.copy();
            }
        });

        // ActionListener para el elemento "Pegar" del menú
        pegarItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            	// Pega el texto del portapapeles en la posición del cursor
            	editorPane.paste();
            }
        });
    }
}
