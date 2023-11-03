package ajedrez;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ChessBoardGUI {
    private JFrame marco;
    private JPanel panelTablero;
    private DefaultListModel<String> listaMovimientosModelo;
    private JList<String> listaMovimientos;
    private boolean turnoBlancas = true;
    private static final int TAMANIO_TABLERO = 8;
    private static final int TAMANIO_CASILLA = 80;
    private Map<JPanel, String> posicionesPiezas;
    private JPanel casillaSeleccionada;   
    private JLabel etiquetaTurno;
    private ButtonGroup grupoColores;
    private JRadioButton blancoButton;
    private JRadioButton grisButton;   

    public ChessBoardGUI() {
        marco = new JFrame("Tablero de Ajedrez");
        marco.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        marco.setLayout(new BorderLayout());

        panelTablero = new JPanel(new GridLayout(TAMANIO_TABLERO, TAMANIO_TABLERO));
        listaMovimientosModelo = new DefaultListModel<>();
        listaMovimientos = new JList<>(listaMovimientosModelo);

        posicionesPiezas = new HashMap<>();             
        
        etiquetaTurno = new JLabel("Turno de las Blancas");        
               
        grupoColores = new ButtonGroup();
        blancoButton = new JRadioButton("Blanco");
        grisButton = new JRadioButton("Gris");
        // Establecer el radioBlanco como seleccionado por defecto
        blancoButton.setSelected(true);
        
        grupoColores.add(blancoButton);
        grupoColores.add(grisButton);

        blancoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarColorTablero(Color.WHITE, Color.LIGHT_GRAY);
            }
        });

        grisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarColorTablero(Color.GRAY, Color.DARK_GRAY);
            }
        });

        JPanel panelOpciones = new JPanel();
        panelOpciones.add(new JLabel("Color del tablero: "));
        panelOpciones.add(blancoButton);
        panelOpciones.add(grisButton);
        marco.add(panelOpciones, BorderLayout.NORTH);            
        
        inicializarTablero();
        configurarInterfaz();
        actualizarLabelTurno(); // Agrega esta línea para inicializar el label del turno
       
    }    
       
    private void cambiarColorTablero(Color colorCasillasBlancas, Color colorCasillasNegras) {
        for (Component componente : panelTablero.getComponents()) {
            if (componente instanceof JPanel) {
                JPanel casilla = (JPanel) componente;
                int indiceCasilla = panelTablero.getComponentZOrder(casilla);
                int fila = indiceCasilla / TAMANIO_TABLERO;
                int columna = indiceCasilla % TAMANIO_TABLERO;
                Color colorCasilla = (fila + columna) % 2 == 0 ? colorCasillasBlancas : colorCasillasNegras;
                casilla.setBackground(colorCasilla);
            }
        }
    }

    private void configurarInterfaz() {
    	JPanel panelBotones = new JPanel();    
        
        panelBotones.add(etiquetaTurno);

        marco.add(panelTablero, BorderLayout.CENTER);
        marco.add(new JScrollPane(listaMovimientos), BorderLayout.EAST);
        marco.add(panelBotones, BorderLayout.SOUTH);
               
        marco.pack();
        marco.setLocationRelativeTo(null);
        marco.setVisible(true);
    }

    private void inicializarTablero() {
        for (int fila = 0; fila < TAMANIO_TABLERO; fila++) {
            for (int columna = 0; columna < TAMANIO_TABLERO; columna++) {
                JPanel casilla = new JPanel(new GridBagLayout());
                casilla.setPreferredSize(new Dimension(TAMANIO_CASILLA, TAMANIO_CASILLA));
                casilla.setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);

                panelTablero.add(casilla);
            }
        }

        colocarPiezasIniciales();
    }    
    
    private void actualizarLabelTurno() {
        etiquetaTurno.setText("Turno de las " + (turnoBlancas ? "Blancas" : "Negras"));
    }     
        
    private void colocarPiezasIniciales() {
        for (int fila = 0; fila < TAMANIO_TABLERO; fila++) {
            for (int columna = 0; columna < TAMANIO_TABLERO; columna++) {
                JPanel casilla = (JPanel) panelTablero.getComponent(fila * TAMANIO_TABLERO + columna);
                String nombrePieza = obtenerNombrePieza(fila, columna);
                if (nombrePieza != null) {
                    ImageIcon icono = cargarImagen("imagenes/" + nombrePieza + ".png");
                    JLabel etiquetaPieza = new JLabel(icono);
                    casilla.add(etiquetaPieza);
                    posicionesPiezas.put(casilla, nombrePieza);
                }
                casilla.addMouseListener(new EscuchaClicPieza());
            }
        }
    }

    private String obtenerNombrePieza(int fila, int columna) {
        if (fila == 1 || fila == 6) {
            return "peon" + (fila == 1 ? "B" : "N");
        } else if (fila == 0 || fila == 7) {
            switch (columna) {
                case 0:
                case 7:
                    return "torre" + (fila == 0 ? "B" : "N");
                case 1:
                case 6:
                    return "caballo" + (fila == 0 ? "B" : "N");
                case 2:
                case 5:
                    return "alfil" + (fila == 0 ? "B" : "N");
                case 3:
                    return "reina" + (fila == 0 ? "B" : "N");
                case 4:
                    return "rey" + (fila == 0 ? "B" : "N");
            }
        }
        return null;
    }

    private ImageIcon cargarImagen(String rutaImagen) {
        try {
            BufferedImage imagen = ImageIO.read(new File(rutaImagen));
            Image imagenEscalada = imagen.getScaledInstance(TAMANIO_CASILLA / 2, TAMANIO_CASILLA / 2, Image.SCALE_SMOOTH);
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class EscuchaClicPieza extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JPanel casillaClickeada = (JPanel) e.getSource();

            if (casillaSeleccionada == null) {
                // Selecciona la casilla solo si contiene una pieza y corresponde al turno actual
                if (posicionesPiezas.containsKey(casillaClickeada) &&
                        posicionesPiezas.get(casillaClickeada).endsWith(turnoBlancas ? "B" : "N")) {
                    casillaSeleccionada = casillaClickeada;
                } else {
                    // Muestra un mensaje de error si la casilla no contiene una pieza del color adecuado
                    JOptionPane.showMessageDialog(null, "Selecciona una pieza de tu color", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (esMovimientoValido(casillaSeleccionada, casillaClickeada)) {
                    moverPieza(casillaSeleccionada, casillaClickeada);
                    casillaSeleccionada = null;
                } else {
                    casillaSeleccionada = null;
                    // Muestra un mensaje de error si el movimiento no es válido
                    JOptionPane.showMessageDialog(null, "Movimiento no válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void moverPieza(JPanel desdeCasilla, JPanel aCasilla) {
            String piezaMovida = posicionesPiezas.get(desdeCasilla);
            String piezaCapturada = posicionesPiezas.get(aCasilla);

            // Elimina la pieza de la casilla de destino (si hay alguna)
            if (piezaCapturada != null) {
            // Si hay una pieza en la casilla de destino, es una captura
            posicionesPiezas.remove(aCasilla);

            // Elimina la etiqueta de la pieza capturada del panel
            Component[] componentesDestino = aCasilla.getComponents();
            for (Component componente : componentesDestino) {
                if (componente instanceof JLabel) {
                    aCasilla.remove(componente);
                    break;
                    }
                }
            }            
         
            // Elimina la pieza de la casilla de origen
            posicionesPiezas.remove(desdeCasilla);

            // Elimina la etiqueta de la pieza movida del panel de origen
            Component[] componentesOrigen = desdeCasilla.getComponents();
            for (Component componente : componentesOrigen) {
                if (componente instanceof JLabel) {
                    desdeCasilla.remove(componente);
                    break;
                }
            }               
            
            // Actualiza la posición de la pieza en el mapa posicionesPiezas
            posicionesPiezas.remove(desdeCasilla);
            posicionesPiezas.put(aCasilla, piezaMovida);

            // Agrega la etiqueta de la pieza al nuevo panel
            ImageIcon icono = cargarImagen("imagenes/" + piezaMovida + ".png");
            JLabel etiquetaPieza = new JLabel(icono);
            aCasilla.add(etiquetaPieza);

            // Actualiza el panel de origen
            desdeCasilla.revalidate();
            desdeCasilla.repaint();

            // Actualiza el panel de destino
            aCasilla.revalidate();
            aCasilla.repaint();

            // Agrega el movimiento al historial de movimientos
            String movimiento = piezaMovida + ": " +
                                obtenerNombreCasilla(desdeCasilla) + " a " +
                                obtenerNombreCasilla(aCasilla);
            if (piezaMovida.endsWith("peon")) {
                movimiento += (piezaCapturada != null ? " (captura)" : "");
            } else {
                movimiento += (piezaCapturada != null ? " (captura " + piezaCapturada + ")" : "");
            }
            if (turnoBlancas) {
                listaMovimientosModelo.addElement(movimiento);
            } else {
                listaMovimientosModelo.add(0, movimiento);
            }
         
            // Registrar el movimiento en el registro y en la base de datos 
            Registro.registrarMovimiento(movimiento);
            BaseDeDatos.guardarMovimientoEnBaseDeDatos(movimiento);

            // Cambia el turno al finalizar un movimiento válido
            cambiarTurno();
        }

    private void cambiarTurno() {
        if (casillaSeleccionada != null) {
            turnoBlancas = !turnoBlancas;
            etiquetaTurno.setText("Turno de " + (turnoBlancas ? "las Blancas" : "las Negras"));
            casillaSeleccionada = null;
        } else {
            JOptionPane.showMessageDialog(null, "Debes seleccionar una pieza y realizar un movimiento válido antes de cambiar de turno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String obtenerNombreCasilla(JPanel casilla) {
            int columna = panelTablero.getComponentZOrder(casilla) % TAMANIO_TABLERO;
            char letraColumna = (char) ('a' + columna);
            int fila = TAMANIO_TABLERO - 1 - panelTablero.getComponentZOrder(casilla) / TAMANIO_TABLERO;
            return "" + letraColumna + fila;
        }

    private boolean esMovimientoValido(JPanel desdeCasilla, JPanel aCasilla) {
            int filaDesde = panelTablero.getComponentZOrder(desdeCasilla) / TAMANIO_TABLERO;
            int columnaDesde = panelTablero.getComponentZOrder(desdeCasilla) % TAMANIO_TABLERO;
            int filaHasta = panelTablero.getComponentZOrder(aCasilla) / TAMANIO_TABLERO;
            int columnaHasta = panelTablero.getComponentZOrder(aCasilla) % TAMANIO_TABLERO;

            String piezaMovida = posicionesPiezas.get(desdeCasilla);

            if (piezaMovida != null) {
                char color = piezaMovida.charAt(piezaMovida.length() - 1);

             // Verificar si la casilla de destino está ocupada por una pieza del mismo color
                if (posicionesPiezas.containsKey(aCasilla) && posicionesPiezas.get(aCasilla).charAt(posicionesPiezas.get(aCasilla).length() - 1) == color) {
                    return false;
                }
                
                if (piezaMovida.startsWith("peon")) {
                    int direccion = (color == 'B') ? 1 : -1;
                    
                    // Verificar si el movimiento es válido en línea vertical (una casilla hacia adelante)
                    boolean movimientoValido = columnaDesde == columnaHasta && Math.abs(filaHasta - filaDesde) == 1 && posicionesPiezas.get(aCasilla) == null;
                    
                    // Código para manejar el movimiento diagonal del peón
                    boolean movimientoDiagonal = Math.abs(columnaDesde - columnaHasta) == 1 && filaHasta - filaDesde == direccion;
                    if (movimientoDiagonal && posicionesPiezas.containsKey(aCasilla) && posicionesPiezas.get(aCasilla).charAt(posicionesPiezas.get(aCasilla).length() - 1) != color) {
                        // Movimiento diagonal válido solo si hay una pieza del oponente para capturar
                        return true;
                    }
                    
                    // Código para manejar el movimiento vertical del peón
                    if (movimientoValido) {
                        
                        return true; // El movimiento del peón es válido
                    }
                    
                } else if (piezaMovida.startsWith("torre")) {
                    // Verificar si el movimiento es vertical u horizontal y si no hay ninguna pieza en el camino
                    if ((filaDesde == filaHasta && esCaminoVerticalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) ||
                        (columnaDesde == columnaHasta && esCaminoHorizontalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta))) {
                        // Verificar si hay alguna pieza en el camino vertical u horizontal
                        if (esCaminoVerticalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta) &&
                            esCaminoHorizontalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) {
                            return true; // El movimiento de la torre es válido
                        }
                    }
                } else if (piezaMovida.startsWith("caballo")) {
                    // Verificar si el movimiento es en forma de "L" (dos casillas en una dirección y una casilla en la dirección perpendicular)
                    int difFila = Math.abs(filaHasta - filaDesde);
                    int difColumna = Math.abs(columnaHasta - columnaDesde);
                    if ((difFila == 2 && difColumna == 1) || (difFila == 1 && difColumna == 2)) {
                        return true; // El movimiento del caballo es válido
                    }
                } else if (piezaMovida.startsWith("alfil")) {
                    // Verificar si el movimiento es diagonal y si no hay ninguna pieza en el camino diagonal
                    if (Math.abs(filaHasta - filaDesde) == Math.abs(columnaHasta - columnaDesde) &&
                        esCaminoDiagonalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) {
                        return true; // El movimiento del alfil es válido
                    }
                } else if (piezaMovida.startsWith("reina")) {
                    // Verificar si el movimiento es vertical, horizontal o diagonal y si no hay ninguna pieza en el camino
                    if ((filaDesde == filaHasta && esCaminoVerticalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) ||
                        (columnaDesde == columnaHasta && esCaminoHorizontalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) ||
                        (Math.abs(filaHasta - filaDesde) == Math.abs(columnaHasta - columnaDesde) &&
                         esCaminoDiagonalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta))) {
                        return true; // El movimiento de la reina es válido
                    }
                } else if (piezaMovida.startsWith("rey")) {
                    // Verificar si el movimiento es en una casilla adyacente (vertical, horizontal o diagonal)
                    if (Math.abs(filaHasta - filaDesde) <= 1 && Math.abs(columnaHasta - columnaDesde) <= 1) {
                        return true; // El movimiento del rey es válido
                    }
                }
             // Si ninguna de las condiciones anteriores se cumple, el movimiento no es válido para la pieza movida
                return false;
            }
            // Si la pieza movida es null, el movimiento no es válido
            return false;
        }        

    private boolean esCaminoVerticalLibre(int filaDesde, int columnaDesde, int filaHasta, int columnaHasta) {
            int inicio = Math.min(filaDesde, filaHasta) + 1;
            int fin = Math.max(filaDesde, filaHasta);
            for (int i = inicio; i < fin; i++) {
                if (posicionesPiezas.containsKey(obtenerCasilla(i, columnaDesde))) {
                    return false;
                }
            }
            return true;
        }

    private boolean esCaminoHorizontalLibre(int filaDesde, int columnaDesde, int filaHasta, int columnaHasta) {
            int inicio = Math.min(columnaDesde, columnaHasta) + 1;
            int fin = Math.max(columnaDesde, columnaHasta);
            for (int i = inicio; i < fin; i++) {
                if (posicionesPiezas.containsKey(obtenerCasilla(filaDesde, i))) {
                    return false;
                }
            }
            return true;
        }

    private boolean esCaminoDiagonalLibre(int filaDesde, int columnaDesde, int filaHasta, int columnaHasta) {
            int difFila = Math.abs(filaHasta - filaDesde);
            int incrementoFila = (filaHasta > filaDesde) ? 1 : -1;
            int incrementoColumna = (columnaHasta > columnaDesde) ? 1 : -1;
            int fila = filaDesde + incrementoFila;
            int columna = columnaDesde + incrementoColumna;
            for (int i = 0; i < difFila - 1; i++) {
                if (posicionesPiezas.containsKey(obtenerCasilla(fila, columna))) {
                    return false;
                }
                fila += incrementoFila;
                columna += incrementoColumna;
            }
            return true;
        }

    private JPanel obtenerCasilla(int fila, int columna) {
            Component[] componentes = panelTablero.getComponents();
            return (JPanel) componentes[fila * TAMANIO_TABLERO + columna];
        	}
    }