package ajedrez;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Registro {
    private static final String NOMBRE_ARCHIVO = "documentos\\registro.txt";

    public static void registrarMovimiento(String movimiento) {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String fechaHoraFormateada = ahora.format(formatoFechaHora);
            escritor.println(fechaHoraFormateada + " - " + movimiento);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
