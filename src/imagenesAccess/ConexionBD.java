package imagenesAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection obtenerConexion() throws SQLException {
        String url = "jdbc:ucanaccess://BD/datos.accdb";
        return DriverManager.getConnection(url);
    }
}
