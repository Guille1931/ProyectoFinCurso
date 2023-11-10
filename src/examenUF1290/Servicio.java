package examenUF1290;

import java.io.Serializable;

public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    // Atributos de la clase
    private String fecha;
    private String concepto;
    private String tecnico;
    private String categoria;
    private boolean terminado;

    // Constructor de la clase
    public Servicio(String fecha, String concepto, String tecnico, String categoria) {
        this.fecha = fecha;
        this.concepto = concepto;
        this.tecnico = tecnico;
        this.categoria = categoria;
        this.terminado = false; // Por defecto, el servicio no está terminado al ser creado.
    }

    // Métodos de acceso (getters y setters) para cada atributo

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public boolean isTerminado() {
        return terminado;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    // Método toString para obtener una representación en cadena del objeto
    @Override
    public String toString() {
        return "Fecha: " + fecha + "\n" +
               "Concepto: " + concepto + "\n" +
               "Técnico: " + tecnico + "\n" +
               "Categoría: " + categoria + "\n" +
               "Terminado: " + terminado;
    }
}
