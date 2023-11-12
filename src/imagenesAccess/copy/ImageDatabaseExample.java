package imagenesAccess.copy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class ImageDatabaseExample {

    public static void main(String[] args) {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:ucanaccess://BD/datos.accdb";
        String user = ""; // Puedes dejarlo vacío si no hay usuario/password
        String password = "";

        try {
            // Establecer la conexión
            Connection connection = DriverManager.getConnection(url, user, password);

            // Guardar imagen en la base de datos
            insertImage(connection, "TuTabla", "CampoImagen", "imagenes/logoColor.png");

            // Recuperar imagen de la base de datos
            BufferedImage image = retrieveImage(connection, "TuTabla", "CampoImagen", 13); // Reemplaza 1 con el ID correcto

            // Mostrar la imagen en una interfaz de usuario
            displayImage(image);

            // Cerrar la conexión
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    // Método para insertar una imagen en la base de datos
    private static void insertImage(Connection connection, String tableName, String columnName, String imagePath)
            throws SQLException, IOException {
        String sql = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            File imageFile = new File(imagePath);
            BufferedImage bufferedImage = ImageIO.read(imageFile);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            pstmt.setBytes(1, imageBytes);
            pstmt.executeUpdate();
        }
    }

    private static BufferedImage retrieveImage(Connection connection, String tableName, String columnName, int id)
            throws SQLException, IOException {
    	String sql = "SELECT CampoImagen FROM TuTabla WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes(columnName);
                ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                return ImageIO.read(bais);
            }
        }

        return null;
    }

    // Método para mostrar la imagen en una interfaz de usuario
    private static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        if (image != null) {
            JLabel label = new JLabel(new ImageIcon(image));
            frame.getContentPane().add(label);
        } else {
            frame.getContentPane().add(new JLabel("No se pudo recuperar la imagen."));
        }

        frame.pack();
        frame.setVisible(true);
    }
}
