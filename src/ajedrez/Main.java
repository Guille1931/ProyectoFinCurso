package ajedrez;

import javax.swing.SwingUtilities;//La clase SwingUtilities  proporciona utilidades relacionadas con la interfaz gráfica de usuario (GUI) de Swing.

//Clase principal del programa que contiene el método main.
public class Main {
 // Método principal que se ejecuta al iniciar la aplicación.
 public static void main(String[] args) {
     // Invoca la interfaz gráfica de usuario en un hilo de despacho de eventos para asegurar su correcta ejecución.
     SwingUtilities.invokeLater(() -> new ChessBoardGUI());
 }

}
