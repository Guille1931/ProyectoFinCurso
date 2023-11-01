package ajedrez;

public class MovimientoRegistro {
    private String movimiento;
    private char color;
    private long tiempo; // Tiempo en milisegundos

    public MovimientoRegistro(String movimiento, char color, long tiempo) {
        this.movimiento = movimiento;
        this.color = color;
        this.tiempo = tiempo;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public char getColor() {
        return color;
    }

    public long getTiempo() {
        return tiempo;
    }
}
