package ajedrez;

import javax.swing.SwingUtilities;


public class Main {
 // Método principal que se ejecuta al iniciar la aplicación.
 public static void main(String[] args) {
     // Invoca la interfaz gráfica de usuario en un hilo de despacho de eventos para asegurar su correcta ejecución.
     SwingUtilities.invokeLater(() -> new ChessBoardGUI());
 }
}
