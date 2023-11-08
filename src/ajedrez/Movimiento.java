package ajedrez;

public class Movimiento {
    private String columna1;
    private String columna2;

    public Movimiento(String columna1, String columna2) {
        this.columna1 = columna1;
        this.columna2 = columna2;
    }

    public String getColumna1() {
        return columna1;
    }

    public String getColumna2() {
        return columna2;
    }
}
