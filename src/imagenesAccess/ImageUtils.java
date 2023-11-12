package imagenesAccess;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ImageUtils {
    public static byte[] leerImagenComoBytes(String rutaImagen) {
        byte[] imagenBytes = null;
        try {
            Path path = Path.of(rutaImagen);
            imagenBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagenBytes;
    }
}
