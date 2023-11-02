package ajedrez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDeDatos {
    private static List<String> movimientosGuardados = new ArrayList<>();
    private static final String URL = "jdbc:ucanaccess://BD\\Partidas.accdb";

    public static void guardarMovimientoEnBaseDeDatos(String movimiento) {
        movimientosGuardados.add(movimiento);
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Partida (movimiento) VALUES (?)")) {
            preparedStatement.setString(1, movimiento);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> obtenerMovimientosGuardados() {
        movimientosGuardados.clear();
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT movimiento FROM Partida");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String movimiento = resultSet.getString("movimiento");
                movimientosGuardados.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(movimientosGuardados);
    }
}
