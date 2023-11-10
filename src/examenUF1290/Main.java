package examenUF1290;

public class Main {
    public static void main(String[] args) {
        // Invoca la interfaz de usuario en el hilo de despacho de eventos de Swing
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Crea una nueva instancia de la interfaz de gestión de servicios
                new GestorServiciosUI();
            }
        });
    }
}
