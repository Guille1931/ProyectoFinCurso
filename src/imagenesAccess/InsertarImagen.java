package imagenesAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarImagen {
    public static void insertarImagen(String rutaImagen) {
        String sql = "INSERT INTO TuTabla (RutaImagen, CampoImagen) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            // Setear el valor del parámetro de la ruta de la imagen
            pstmt.setString(1, rutaImagen);

            // Leer la imagen desde el archivo
            byte[] imagen = ImageUtils.leerImagenComoBytes(rutaImagen);

            // Establecer el valor del parámetro de la imagen
            pstmt.setBytes(2, imagen);

            // Ejecutar la inserción
            pstmt.executeUpdate();

            System.out.println("Imagen insertada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
