package ajedrez;

import java.sql.Connection; // Proporciona métodos para establecer una conexión con una base de datos.
import java.sql.DriverManager; // Implementa el administrador de controladores JDBC para cargar controladores de bases de datos.
import java.sql.PreparedStatement; // Permite ejecutar consultas SQL precompiladas en una base de datos.
import java.sql.ResultSet; // Representa un conjunto de resultados de una consulta SQL.
import java.sql.SQLException; // Maneja excepciones específicas de SQL que pueden ocurrir durante la ejecución de operaciones de base de datos.
import java.util.ArrayList; // Implementación de la interfaz List que utiliza un arreglo para almacenar elementos.
import java.util.List; // Interfaz que representa una lista ordenada de elementos.

public class BaseDeDatos {
    // Lista para almacenar movimientos guardados en memoria antes de ser almacenados en la base de datos
    private static List<String> movimientosGuardados = new ArrayList<>();
    // URL de conexión a la base de datos Access
    private static final String URL = "jdbc:ucanaccess://BD\\Partidas.accdb";


    // Método para guardar un movimiento en la base de datos
    public static void guardarMovimientoEnBaseDeDatos(String movimiento) {
        // Agrega el movimiento a la lista de movimientos guardados en memoria
        movimientosGuardados.add(movimiento);
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Partida (movimiento) VALUES (?)")) {
            // Prepara la consulta SQL para insertar el movimiento en la base de datos
            preparedStatement.setString(1, movimiento);
            // Ejecuta la consulta para insertar el movimiento en la tabla 'Partida'
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        	// Maneja errores de SQL imprimiendo la traza de la excepción
            e.printStackTrace();
        }
    }

    // Método para obtener todos los movimientos guardados en la base de datos
    public static List<String> obtenerMovimientosGuardados() {
        // Limpia la lista de movimientos guardados en memoria
        movimientosGuardados.clear();
        try (Connection connection = DriverManager.getConnection(URL);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT movimiento FROM Partida");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            // Ejecuta una consulta SQL para obtener todos los movimientos de la tabla 'Partida'
            while (resultSet.next()) {
                String movimiento = resultSet.getString("movimiento");
                // Agrega cada movimiento obtenido de la base de datos a la lista en memoria
                movimientosGuardados.add(movimiento);
            }
        } catch (SQLException e) {
        	// Maneja errores de SQL imprimiendo la traza de la excepción
            e.printStackTrace();
        }
        // Devuelve una nueva lista con los movimientos guardados en memoria
        return new ArrayList<>(movimientosGuardados);
    }
}





