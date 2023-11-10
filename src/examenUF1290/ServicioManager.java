package examenUF1290;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServicioManager implements Serializable {
    
    private static final long serialVersionUID = 1L;

    // Listas para almacenar servicios según su categoría
    private List<Servicio> serviciosUrgentes = new ArrayList<>();
    private List<Servicio> serviciosNormales = new ArrayList<>();
    private List<Servicio> serviciosPospuestos = new ArrayList<>();
    
    // Lista que contiene todos los servicios
    private List<Servicio> serviciosTotales = new ArrayList<>();

    // Método para agregar un servicio y clasificarlo según su categoría
    public void agregarServicio(Servicio servicio) {
        serviciosTotales.add(servicio);

        // Clasificar el servicio en la lista correspondiente
        if ("Urgente".equals(servicio.getCategoria())) {
            serviciosUrgentes.add(servicio);
        } else if ("Normal".equals(servicio.getCategoria())) {
            serviciosNormales.add(servicio);
        } else if ("Pospuesto".equals(servicio.getCategoria())) {
            serviciosPospuestos.add(servicio);
        }
    }

    // Métodos para obtener copias de las listas de servicios según su categoría o totales
    public List<Servicio> obtenerServiciosUrgentes() {
        return new ArrayList<>(serviciosUrgentes);
    }

    public List<Servicio> obtenerServiciosNormales() {
        return new ArrayList<>(serviciosNormales);
    }

    public List<Servicio> obtenerServiciosPospuestos() {
        return new ArrayList<>(serviciosPospuestos);
    }

    public List<Servicio> obtenerServiciosTotales() {
        return new ArrayList<>(serviciosTotales);
    }

    // Métodos para contar la cantidad de servicios según su categoría
    public int contarServiciosUrgentes() {
        return serviciosUrgentes.size();
    }

    public int contarServiciosNormales() {
        return serviciosNormales.size();
    }

    public int contarServiciosPospuestos() {
        return serviciosPospuestos.size();
    }
}
