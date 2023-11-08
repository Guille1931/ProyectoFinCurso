package ajedrez;

import java.io.FileWriter; // Para escribir en un archivo.
import java.io.IOException; // Para manejar excepciones de entrada/salida.
import java.io.PrintWriter; // Para escribir texto en un archivo.
import java.time.LocalDateTime; // Para obtener la fecha y hora actual.
import java.time.format.DateTimeFormatter; // Para formatear la fecha y hora.

//Clase que proporciona métodos para registrar movimientos en un archivo de registro.
public class Registro {
 // Ruta del archivo de registro donde se almacenarán los movimientos.
 private static final String NOMBRE_ARCHIVO = "documentos\\registro.txt";

 // Método estático que registra un movimiento junto con su marca de tiempo en el archivo de registro.
 public static void registrarMovimiento(String movimiento) {
     try (PrintWriter escritor = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
         // Obtiene la fecha y hora actual.
         LocalDateTime ahora = LocalDateTime.now();
         // Formatea la fecha y hora utilizando el patrón "dd-MM-yyyy HH:mm:ss".
         DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
         String fechaHoraFormateada = ahora.format(formatoFechaHora);
         // Escribe la marca de tiempo y el movimiento en el archivo de registro.
         escritor.println(fechaHoraFormateada + " - " + movimiento);
     } catch (IOException e) {
         e.printStackTrace(); // Manejo de errores en caso de problemas al escribir en el archivo.
     }
   }
}