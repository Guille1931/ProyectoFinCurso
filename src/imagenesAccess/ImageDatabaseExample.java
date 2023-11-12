package imagenesAccess;

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
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ImageDatabaseExample {

	 public static void main(String[] args) throws SQLException {
	        // Configuración de la conexión a la base de datos HSQLDB
	        String url = "jdbc:hsqldb:mem:testdb";
	        Connection connection = DriverManager.getConnection(url);

	        try {
	            // Establecer la conexión
	            Connection connection1 = DriverManager.getConnection(url);

	            // Crear la tabla en HSQLDB
	            createTable(connection1);

	            // Guardar imagen en la base de datos
	            insertImage(connection1, "TuTabla", "CampoImagen", "imagenes/logoColor.png");

	            // Recuperar imagen de la base de datos
	            BufferedImage image = retrieveImage(connection1, "TuTabla", "CampoImagen", 12);

	            // Mostrar la imagen en una interfaz de usuario
	            displayImage(image);

	            // Cerrar la conexión
	            connection1.close();
	        } catch (SQLException | IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	// Método para crear la tabla en HSQLDB
	    private static void createTable(Connection connection) throws SQLException {
	        try (Statement stmt = connection.createStatement()) {
	            // Define la estructura de la tabla
	            String createTableSQL = "CREATE TABLE TuTabla (id INTEGER, CampoImagen BLOB)";
	            stmt.execute(createTableSQL);
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
        frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
        		FormSpecs.RELATED_GAP_COLSPEC,
        		ColumnSpec.decode("89px"),
        		FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
        		ColumnSpec.decode("46px"),},
        	new RowSpec[] {
        		FormSpecs.RELATED_GAP_ROWSPEC,
        		RowSpec.decode("23px"),}));
        frame.setVisible(true);
    }
}
