package editorTexto;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Punto de entrada principal del programa
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            // Este bloque se ejecuta en el hilo de eventos de Swing para asegurar la correcta manipulación de la interfaz gráfica
            public void run() {
            	// Crea una instancia del editor de texto
                EditorTexto editor = new EditorTexto(); 
                // Hace visible la ventana del editor de texto
                editor.setVisible(true); 
            }
        });
    }
}

