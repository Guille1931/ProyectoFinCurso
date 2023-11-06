package ajedrez;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class VentanaNuevaPartida extends JFrame {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static void ventanaNuevaPartida(String[] args) {
        // Crea un nuevo JFrame con el título "Nueva Partida"
        JFrame frame = new JFrame("Nueva Partida");

        // Muestra un cuadro de diálogo de confirmación para preguntar si queremos empezar una nueva partida
        int opcion = JOptionPane.showConfirmDialog(frame, "¿Quieres empezar una nueva partida?", "Nueva Partida", JOptionPane.YES_NO_OPTION);

        // Verifica la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            // Si el usuario selecciona "Sí", muestra un mensaje
            JOptionPane.showMessageDialog(frame, "Empezando una nueva partida...");
        } else {
            // Si el usuario selecciona "No", muestra un mensaje de despedida
            JOptionPane.showMessageDialog(frame, "¡Hasta luego! Gracias por jugar.");
        }

        // Cierra la aplicación cuando se cierra la ventana
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Establece el tamaño de la ventana
        frame.setSize(300, 200);
        // Centra la ventana en la pantalla
        frame.setLocationRelativeTo(null);
        // Hace visible la ventana
        frame.setVisible(true);
    }
}