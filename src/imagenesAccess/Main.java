package imagenesAccess;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    public static void main(String[] args) {
        // Ruta de la imagen que deseas insertar y mostrar
        String rutaImagen = "imagenes/logoColor.png";

        // Llamar al método para insertar la imagen en la base de datos
        InsertarImagen.insertarImagen(rutaImagen);

        // Mostrar la imagen utilizando un control de imagen (Swing en este caso)
        mostrarImagen(rutaImagen);
    }

    private static void mostrarImagen(String rutaImagen) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un JLabel y establecer la imagen
        JLabel label = new JLabel(new ImageIcon(rutaImagen));
        frame.getContentPane().add(label);

        frame.pack();
        frame.setVisible(true);
    }
}
