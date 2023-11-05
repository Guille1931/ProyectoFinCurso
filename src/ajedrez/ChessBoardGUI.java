package ajedrez;

import java.awt.*; // Proporciona clases para la creación de interfaces gráficas de usuario (GUI).
import java.awt.event.*; // Proporciona clases para manejar eventos generados por componentes de la GUI.
import java.awt.image.BufferedImage; // Proporciona clases para trabajar con imágenes en memoria.
import java.io.File; // Proporciona clases para manejar archivos y directorios en el sistema de archivos.
import java.io.IOException; // Proporciona clases para manejar excepciones de entrada/salida.
import java.util.HashMap; // Proporciona una implementación de mapa basada en tablas de dispersión.
import java.util.Map; // Proporciona una interfaz para asociar claves con valores.

import javax.imageio.ImageIO; // Proporciona clases para leer y escribir imágenes en diferentes formatos.
import javax.swing.*; // Proporciona componentes y clases para la creación de aplicaciones GUI.


public class ChessBoardGUI {
   
    private JFrame marco;  // Marco principal de la aplicación
    private JPanel panelTablero;  // Panel que contiene el tablero de ajedrez
    private DefaultListModel<String> listaMovimientosModelo;  // Modelo para la lista de movimientos
    private JList<String> listaMovimientos;  // Lista de movimientos
    private boolean turnoBlancas = true;  // Variable para controlar el turno de las blancas
    private static final int TAMANIO_TABLERO = 8;  // Tamaño del tablero (8x8)
    private static final int TAMANIO_CASILLA = 80;  // Tamaño de cada casilla del tablero
    private Map<JPanel, String> posicionesPiezas;  // Mapa que almacena las posiciones de las piezas en el tablero
    private JPanel casillaSeleccionada;  // Casilla seleccionada por el usuario para realizar un movimiento
    private JLabel etiquetaTurno;  // Etiqueta que muestra el turno actual (blancas o negras)
    private ButtonGroup grupoColores;  // Grupo de botones para seleccionar el color del tablero
    private JRadioButton blancoButton;  // Botón para seleccionar el color blanco del tablero
    private JRadioButton grisButton;  // Botón para seleccionar el color gris del tablero
    private boolean partidaFinalizada; // Variable para indicar si la partida ha finalizado
    
    // Constructor de la clase ChessBoardGUI que inicializa la interfaz gráfica del tablero de ajedrez
    public ChessBoardGUI() {
        // Crear un nuevo JFrame con el título "Tablero de Ajedrez"
        marco = new JFrame("Tablero de Ajedrez");
        // Establecer la operación de cierre del JFrame al cerrar la ventana
        marco.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Establecer el diseño del JFrame como BorderLayout
        marco.setLayout(new BorderLayout());

        // Crear un nuevo panel con un GridLayout para representar el tablero de ajedrez
        panelTablero = new JPanel(new GridLayout(TAMANIO_TABLERO, TAMANIO_TABLERO));
        // Crear un modelo para la lista de movimientos y asociarlo con una JList
        listaMovimientosModelo = new DefaultListModel<>();
        listaMovimientos = new JList<>(listaMovimientosModelo);

        // Inicializar el mapa que almacena las posiciones de las piezas en el tablero
        posicionesPiezas = new HashMap<>();

        // Crear una etiqueta para mostrar el turno actual
        etiquetaTurno = new JLabel("Turno de las Blancas");

        // Crear un grupo de botones de radio para los colores del tablero
        grupoColores = new ButtonGroup();
        // Crear botones de radio para los colores Blanco y Gris, con Blanco seleccionado por defecto
        blancoButton = new JRadioButton("Blanco");
        grisButton = new JRadioButton("Gris");
        // Establecer el botón de Blanco como seleccionado por defecto
        blancoButton.setSelected(true); 

        // Agregar los botones de radio al grupo de botones
        grupoColores.add(blancoButton);
        grupoColores.add(grisButton);

        // Agregar listeners para los botones de radio para cambiar el color del tablero
        blancoButton.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
                cambiarColorTablero(Color.WHITE, Color.LIGHT_GRAY);
            }
        });
        grisButton.addActionListener(new ActionListener() {          
            public void actionPerformed(ActionEvent e) {
                cambiarColorTablero(Color.GRAY, Color.DARK_GRAY);
            }
        });
        
        
        JPanel panelOpciones = new JPanel();
        panelOpciones.add(new JLabel("Color del tablero: "));
        panelOpciones.add(blancoButton);
        panelOpciones.add(grisButton);
        // Agregar el panel de opciones en la parte superior del JFrame usando BorderLayout
        marco.add(panelOpciones, BorderLayout.NORTH);

        // Inicializar el tablero y configurar la interfaz gráfica
        inicializarTablero();
        configurarInterfaz();
        // Actualizar el label del turno para mostrar el turno inicial
        actualizarLabelTurno(); 
    }
    
 // Método para bloquear los movimientos y mostrar un mensaje de fin de partida
    public void bloquearMovimientos(String mensaje) {
        // Muestra un mensaje informando del final de la partida
        JOptionPane.showMessageDialog(marco, mensaje, "Fin de la Partida", JOptionPane.INFORMATION_MESSAGE);
        // Bloquea cualquier movimiento deshabilitando el MouseListener de las casillas
        for (Component componente : panelTablero.getComponents()) {
            if (componente instanceof JPanel) {
                JPanel casilla = (JPanel) componente;
                casilla.removeMouseListener(casilla.getMouseListeners()[0]);
            }
        }
    }
    
    public String obtenerNombrePiezaDesdeCasilla(JPanel casilla) {
        // Verifica si la casilla tiene una pieza
        if (posicionesPiezas.containsKey(casilla)) {
            return posicionesPiezas.get(casilla);
        }
        return null; // Retorna null si la casilla está vacía
    }

    public void mostrarMensajeFinDePartida(String ganador) {
        JOptionPane.showMessageDialog(null, "¡Partida finalizada! Las " + ganador + " ganan.", "Fin de la partida", JOptionPane.INFORMATION_MESSAGE);
    }  
       
    // Método para cambiar el color del tablero de ajedrez según los colores especificados
    private void cambiarColorTablero(Color colorCasillasBlancas, Color colorCasillasNegras) {
        // Iterar a través de todos los componentes (casillas) en el panel del tablero
        for (Component componente : panelTablero.getComponents()) {
            // Verificar si el componente actual es un panel
            if (componente instanceof JPanel) {
                // Obtener el panel actual (casilla)
                JPanel casilla = (JPanel) componente;
                // Obtener el índice de la casilla en el panel del tablero
                int indiceCasilla = panelTablero.getComponentZOrder(casilla);
                // Calcular la fila y la columna de la casilla actual en el tablero
                int fila = indiceCasilla / TAMANIO_TABLERO;
                int columna = indiceCasilla % TAMANIO_TABLERO;
                // Determinar el color de la casilla según su posición en el tablero (par o impar)
                Color colorCasilla = (fila + columna) % 2 == 0 ? colorCasillasBlancas : colorCasillasNegras;
                // Establecer el color de fondo del panel actual al color calculado
                casilla.setBackground(colorCasilla);
            }
        }
    }


    // Método para configurar la interfaz gráfica del tablero de ajedrez
    private void configurarInterfaz() {
        // Crear un nuevo panel para mostrar la etiqueta del turno de las piezas
        JPanel panelBotones = new JPanel();
        // Agregar la etiqueta del turno al panel de botones
        panelBotones.add(etiquetaTurno);
        // Agregar el panel del tablero al centro del marco usando BorderLayout
        marco.add(panelTablero, BorderLayout.CENTER);
        // Agregar una barra de desplazamiento a la lista de movimientos y colocarla en el lado derecho del marco
        marco.add(new JScrollPane(listaMovimientos), BorderLayout.EAST);
        // Agregar el panel de botones en la parte inferior del marco usando BorderLayout
        marco.add(panelBotones, BorderLayout.SOUTH);
        // Ajustar el tamaño del marco según su contenido
        marco.pack();
        // Centrar el marco en la pantalla
        marco.setLocationRelativeTo(null);
        // Hacer visible el marco para mostrar la interfaz gráfica
        marco.setVisible(true);
    }


    // Método para inicializar el tablero de ajedrez con casillas y piezas
    private void inicializarTablero() {
        // Iterar sobre las filas del tablero
        for (int fila = 0; fila < TAMANIO_TABLERO; fila++) {
            // Iterar sobre las columnas del tablero
            for (int columna = 0; columna < TAMANIO_TABLERO; columna++) {
                // Crear un nuevo panel (casilla) con un diseño de cuadrícula y tamaño definidos
                JPanel casilla = new JPanel(new GridBagLayout());
                casilla.setPreferredSize(new Dimension(TAMANIO_CASILLA, TAMANIO_CASILLA));
                // Establecer el fondo de la casilla como blanco o gris claro según su posición en el tablero
                casilla.setBackground((fila + columna) % 2 == 0 ? Color.WHITE : Color.LIGHT_GRAY);
                // Agregar la casilla al panel del tablero
                panelTablero.add(casilla);
            }
        }
        // Colocar las piezas iniciales en el tablero
        colocarPiezasIniciales();
    }

    
    // Método para actualizar la etiqueta del turno de las piezas en la interfaz gráfica
    private void actualizarLabelTurno() {
        // Actualiza el texto de la etiqueta según el turno actual (Blancas o Negras)
        etiquetaTurno.setText("Turno de las " + (turnoBlancas ? "Blancas" : "Negras"));
    }
  
        
    // Método para colocar las piezas iniciales en el tablero de ajedrez
    private void colocarPiezasIniciales() {
        // Iterar sobre las filas del tablero
        for (int fila = 0; fila < TAMANIO_TABLERO; fila++) {
            // Iterar sobre las columnas del tablero
            for (int columna = 0; columna < TAMANIO_TABLERO; columna++) {
                // Obtener la casilla (panel) en la posición actual
                JPanel casilla = (JPanel) panelTablero.getComponent(fila * TAMANIO_TABLERO + columna);
                // Obtener el nombre de la pieza en la posición actual
                String nombrePieza = obtenerNombrePieza(fila, columna);
                // Verificar si hay una pieza en la posición actual
                if (nombrePieza != null) {
                    // Cargar la imagen de la pieza desde el archivo correspondiente
                    ImageIcon icono = cargarImagen("imagenes/" + nombrePieza + ".png");
                    // Crear una etiqueta con la imagen de la pieza
                    JLabel etiquetaPieza = new JLabel(icono);
                    // Agregar la etiqueta de la pieza al panel de la casilla
                    casilla.add(etiquetaPieza);
                    // Almacenar la posición de la pieza en el mapa posicionesPiezas
                    posicionesPiezas.put(casilla, nombrePieza);
                }
                // Agregar un escuchador de clic a la casilla para gestionar los movimientos
                casilla.addMouseListener(new EscuchaClicPieza());
            }
        }
    }


    // Método para obtener el nombre de la pieza en una posición específica del tablero
    private String obtenerNombrePieza(int fila, int columna) {
        // Verificar si la fila corresponde a la posición de un peón (fila 1 o fila 6)
        if (fila == 1 || fila == 6) {
            // Si es así, retornar el nombre del peón con el sufijo "B" para blancas o "N" para negras
            return "peon" + (fila == 1 ? "B" : "N");
        } 
        // Verificar si la fila corresponde a la primera o última fila del tablero (filas 0 o 7)
        else if (fila == 0 || fila == 7) {
            // En caso afirmativo, determinar el tipo de pieza según la columna en esa fila
            switch (columna) {
                case 0:
                case 7:
                    // Para las columnas 0 y 7, corresponde a una torre, agregar sufijo "B" o "N" según el color
                    return "torre" + (fila == 0 ? "B" : "N");
                case 1:
                case 6:
                    // Para las columnas 1 y 6, corresponde a un caballo, agregar sufijo "B" o "N" según el color
                    return "caballo" + (fila == 0 ? "B" : "N");
                case 2:
                case 5:
                    // Para las columnas 2 y 5, corresponde a un alfil, agregar sufijo "B" o "N" según el color
                    return "alfil" + (fila == 0 ? "B" : "N");
                case 3:
                    // Para la columna 3, corresponde a una reina, agregar sufijo "B" o "N" según el color
                    return "reina" + (fila == 0 ? "B" : "N");
                case 4:
                    // Para la columna 4, corresponde a un rey, agregar sufijo "B" o "N" según el color
                    return "rey" + (fila == 0 ? "B" : "N");
            }
        }
        // Si no se cumple ninguna de las condiciones anteriores, no hay pieza en esa posición, retornar null
        return null;
    }


    // Método para cargar una imagen desde una ruta específica y escalarla para ajustarla al tamaño de una casilla del tablero
    private ImageIcon cargarImagen(String rutaImagen) {
        try {
            // Lee la imagen desde el archivo en la ruta especificada
            BufferedImage imagen = ImageIO.read(new File(rutaImagen));
            // Escala la imagen para que se ajuste al tamaño de media casilla del tablero de ajedrez
            Image imagenEscalada = imagen.getScaledInstance(TAMANIO_CASILLA / 2, TAMANIO_CASILLA / 2, Image.SCALE_SMOOTH);
            // Crea y devuelve un ImageIcon a partir de la imagen escalada
            return new ImageIcon(imagenEscalada);
        } catch (IOException e) {
            // Si ocurre una excepción durante la lectura de la imagen, imprime el error y devuelve null
            e.printStackTrace();
            return null;
        }
    }


    // Clase interna que implementa la interfaz MouseAdapter para manejar los clics del mouse en las casillas del tablero
    private class EscuchaClicPieza extends MouseAdapter {
        
    	// Método que se ejecuta cuando se hace clic en una casilla del tablero
        public void mouseClicked(MouseEvent e) {
            // Obtiene la casilla que ha sido clickeada
            JPanel casillaClickeada = (JPanel) e.getSource();

            // Verifica si no hay una casilla seleccionada previamente
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
                // Verifica si el movimiento es válido
                if (esMovimientoValido(casillaSeleccionada, casillaClickeada)) {
                    // Realiza el movimiento de la pieza
                    moverPieza(casillaSeleccionada, casillaClickeada);
                    // Desselecciona la casilla
                    casillaSeleccionada = null;
                } else {
                    // Desselecciona la casilla y muestra un mensaje de error si el movimiento no es válido
                    casillaSeleccionada = null;
                    JOptionPane.showMessageDialog(null, "Movimiento no válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }         
        }
    }
    
 // Método para verificar si la partida ha finalizado (cuando el rey es capturado)
    public void verificarFinDePartida(String PiezaCapturada) {
        // Obtén el nombre de la pieza en la casilla de destino (si hay alguna)

        // Verifica si la pieza capturada es un rey
        if (PiezaCapturada != null && PiezaCapturada.startsWith("rey")) {
            // La partida ha finalizado, muestra un mensaje y marca la partida como finalizada
            JOptionPane.showMessageDialog(null, "¡Partida finalizada! El rey ha sido capturado.", "Fin de la Partida", JOptionPane.INFORMATION_MESSAGE);
            partidaFinalizada = true;
        }
    }

    // Método para obtener el estado de la partida (si ha finalizado o no)
    public boolean isPartidaFinalizada() {
        return partidaFinalizada;
    }

    // Método para mover una pieza desde una casilla de origen hasta una casilla de destino
    private void moverPieza(JPanel desdeCasilla, JPanel aCasilla) {
        // Obtiene el nombre de la pieza movida y de la pieza capturada (si existe)
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

        // Agrega la etiqueta de la pieza al nuevo panel de destino
        ImageIcon icono = cargarImagen("imagenes/" + piezaMovida + ".png");
        JLabel etiquetaPieza = new JLabel(icono);
        aCasilla.add(etiquetaPieza);

        // Actualiza el panel de origen
        desdeCasilla.revalidate();
        desdeCasilla.repaint();

        // Actualiza el panel de destino
        aCasilla.revalidate();
        aCasilla.repaint();

        // Agrega el movimiento al historial de movimientos en la interfaz gráfica
        String movimiento = piezaMovida + ": " +
                            obtenerNombreCasilla(desdeCasilla) + " a " +
                            obtenerNombreCasilla(aCasilla);
        // Indica si el movimiento implica una captura de pieza
        movimiento += (piezaCapturada != null ? " (captura " + piezaCapturada + ")" : "");
        // Agrega el movimiento al modelo de lista del historial de movimientos
        if (turnoBlancas) {
            listaMovimientosModelo.addElement(movimiento);
        } else {
            listaMovimientosModelo.add(0, movimiento);
        }

        // Registra el movimiento en el registro y en la base de datos (simulado)
        Registro.registrarMovimiento(movimiento);
        BaseDeDatos.guardarMovimientoEnBaseDeDatos(movimiento);
        
        // Cambia el turno al finalizar un movimiento válido
        cambiarTurno();
        // Verifica si la partida ha llegado a su fin
        verificarFinDePartida(piezaCapturada);
    }

    // Método para cambiar el turno del jugador
    private void cambiarTurno() {
        if (casillaSeleccionada != null) {
            // Cambia el valor de la variable turnoBlancas (true a false o false a true)
            turnoBlancas = !turnoBlancas;
            // Actualiza el texto de la etiqueta del turno según el nuevo turno (Blancas o Negras)
            etiquetaTurno.setText("Turno de " + (turnoBlancas ? "las Blancas" : "las Negras"));
            // Reinicia la casilla seleccionada a null para permitir la selección de una nueva pieza
            casillaSeleccionada = null;
        } else {
            // Muestra un mensaje de error si se intenta cambiar de turno sin seleccionar una pieza y moverla
            JOptionPane.showMessageDialog(null, "Debes seleccionar una pieza y realizar un movimiento válido antes de cambiar de turno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener el nombre de una casilla en formato de notación algebráica (por ejemplo, "a1", "e5")
    String obtenerNombreCasilla(JPanel casilla) {
        // Obtiene el índice de la casilla en el panelTablero
        int indiceCasilla = panelTablero.getComponentZOrder(casilla);
        // Calcula la columna de la casilla usando el índice y el tamaño del tablero
        int columna = indiceCasilla % TAMANIO_TABLERO;
        // Convierte el número de columna a letra (a, b, c, ...) usando el valor ASCII
        char letraColumna = (char) ('a' + columna);
        // Calcula la fila de la casilla usando el índice y el tamaño del tablero
        int fila = TAMANIO_TABLERO - 1 - indiceCasilla / TAMANIO_TABLERO;
        // Combina la letra de la columna y el número de fila para formar el nombre de la casilla
        return "" + letraColumna + fila;
    }

    // Método para verificar la validez de un movimiento desde una casilla hasta otra
    private boolean esMovimientoValido(JPanel desdeCasilla, JPanel aCasilla) {
        // Obtiene las coordenadas (fila y columna) de la casilla de origen y destino
        int filaDesde = panelTablero.getComponentZOrder(desdeCasilla) / TAMANIO_TABLERO;
        int columnaDesde = panelTablero.getComponentZOrder(desdeCasilla) % TAMANIO_TABLERO;
        int filaHasta = panelTablero.getComponentZOrder(aCasilla) / TAMANIO_TABLERO;
        int columnaHasta = panelTablero.getComponentZOrder(aCasilla) % TAMANIO_TABLERO;

        // Obtiene el nombre de la pieza en la casilla de origen para determinar su tipo y color
        String piezaMovida = posicionesPiezas.get(desdeCasilla);

        // Verifica si la pieza en la casilla de origen es válida
        if (piezaMovida != null) {
            // Obtiene el color de la pieza en la casilla de origen (último carácter del nombre de la pieza)
            char color = piezaMovida.charAt(piezaMovida.length() - 1);

            // Verifica si la casilla de destino está ocupada por una pieza del mismo color
            if (posicionesPiezas.containsKey(aCasilla) && posicionesPiezas.get(aCasilla).charAt(posicionesPiezas.get(aCasilla).length() - 1) == color) {
                return false; // Movimiento no válido si la casilla de destino está ocupada por una pieza del mismo color
            }

            // Comprueba el tipo de pieza y su movimiento específico
            if (piezaMovida.startsWith("peon")) {
                int direccion = (color == 'B') ? 1 : -1;

                // Verifica si el movimiento del peón es válido en línea vertical (una casilla hacia adelante)
                boolean movimientoVerticalValido = columnaDesde == columnaHasta && Math.abs(filaHasta - filaDesde) == 1 && posicionesPiezas.get(aCasilla) == null;

                // Verifica el movimiento diagonal del peón (captura)
                boolean movimientoDiagonalValido = Math.abs(columnaDesde - columnaHasta) == 1 && filaHasta - filaDesde == direccion &&
                                                  posicionesPiezas.containsKey(aCasilla) &&
                                                  posicionesPiezas.get(aCasilla).charAt(posicionesPiezas.get(aCasilla).length() - 1) != color;

                // El movimiento del peón es válido si es vertical o diagonal (captura)
                return movimientoVerticalValido || movimientoDiagonalValido;
            } else if (piezaMovida.startsWith("torre")) {
                // Verifica si el movimiento de la torre es vertical u horizontal y si no hay piezas en el camino
                return (filaDesde == filaHasta && esCaminoVerticalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) ||
                       (columnaDesde == columnaHasta && esCaminoHorizontalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta));
            } else if (piezaMovida.startsWith("caballo")) {
                // Verifica si el movimiento del caballo sigue la forma de "L" (dos casillas en una dirección y una en la dirección perpendicular)
                int difFila = Math.abs(filaHasta - filaDesde);
                int difColumna = Math.abs(columnaHasta - columnaDesde);
                return (difFila == 2 && difColumna == 1) || (difFila == 1 && difColumna == 2);
            } else if (piezaMovida.startsWith("alfil")) {
                // Verifica si el movimiento del alfil es diagonal y si no hay piezas en el camino
                return Math.abs(filaHasta - filaDesde) == Math.abs(columnaHasta - columnaDesde) &&
                       esCaminoDiagonalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta);
            } else if (piezaMovida.startsWith("reina")) {
                // Verifica si el movimiento de la reina es vertical, horizontal o diagonal y si no hay piezas en el camino
                return (filaDesde == filaHasta && esCaminoVerticalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) ||
                       (columnaDesde == columnaHasta && esCaminoHorizontalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta)) ||
                       (Math.abs(filaHasta - filaDesde) == Math.abs(columnaHasta - columnaDesde) &&
                        esCaminoDiagonalLibre(filaDesde, columnaDesde, filaHasta, columnaHasta));
            } else if (piezaMovida.startsWith("rey")) {
                // Verifica si el movimiento del rey es en una casilla adyacente (vertical, horizontal o diagonal)
                return Math.abs(filaHasta - filaDesde) <= 1 && Math.abs(columnaHasta - columnaDesde) <= 1;
            }
        }
        // Si ninguna de las condiciones anteriores se cumple, el movimiento no es válido para la pieza movida
        return false; 
    }


    // Método para verificar si el camino vertical entre dos casillas está libre de piezas
    private boolean esCaminoVerticalLibre(int filaDesde, int columnaDesde, int filaHasta, int columnaHasta) {
        // Determina el inicio y fin del rango vertical entre las filas de las casillas de origen y destino
        int inicio = Math.min(filaDesde, filaHasta) + 1;
        int fin = Math.max(filaDesde, filaHasta);

        // Itera a través del rango vertical
        for (int i = inicio; i < fin; i++) {
            // Verifica si hay una pieza en la casilla durante el recorrido vertical
            if (posicionesPiezas.containsKey(obtenerCasilla(i, columnaDesde))) {
            	// Retorna falso si hay una pieza en el camino vertical
                return false; 
            }
        }
        // Retorna verdadero si el camino vertical está libre de piezas
        return true; 
    }


    // Método para verificar si el camino horizontal entre dos casillas está libre de piezas
    private boolean esCaminoHorizontalLibre(int filaDesde, int columnaDesde, int filaHasta, int columnaHasta) {
        // Determina el inicio y fin del rango horizontal entre las columnas de las casillas de origen y destino
        int inicio = Math.min(columnaDesde, columnaHasta) + 1;
        int fin = Math.max(columnaDesde, columnaHasta);

        // Itera a través del rango horizontal
        for (int i = inicio; i < fin; i++) {
            // Verifica si hay una pieza en la casilla durante el recorrido horizontal
            if (posicionesPiezas.containsKey(obtenerCasilla(filaDesde, i))) {
            	// Retorna falso si hay una pieza en el camino horizontal
                return false; 
            }
        }
        // Retorna verdadero si el camino horizontal está libre de piezas
        return true; 
    }


    // Método para verificar si el camino diagonal entre dos casillas está libre de piezas
    private boolean esCaminoDiagonalLibre(int filaDesde, int columnaDesde, int filaHasta, int columnaHasta) {
        // Calcula la diferencia en las filas entre las casillas de origen y destino
        int difFila = Math.abs(filaHasta - filaDesde);
        
        // Determina el incremento para las filas y columnas en la dirección diagonal
        int incrementoFila = (filaHasta > filaDesde) ? 1 : -1;
        int incrementoColumna = (columnaHasta > columnaDesde) ? 1 : -1;
        
        // Inicializa las nuevas posiciones de fila y columna para evaluar el camino diagonal
        int fila = filaDesde + incrementoFila;
        int columna = columnaDesde + incrementoColumna;
        
        // Itera a través del camino diagonal
        for (int i = 0; i < difFila - 1; i++) {
            // Verifica si hay una pieza en la casilla durante el recorrido diagonal
            if (posicionesPiezas.containsKey(obtenerCasilla(fila, columna))) {
            	// Retorna falso si hay una pieza en el camino diagonal
                return false; 
            }
            // Actualiza las posiciones de fila y columna para la siguiente iteración en la diagonal
            fila += incrementoFila;
            columna += incrementoColumna;
        }
        // Retorna verdadero si el camino diagonal está libre de piezas
        return true; 
    }


    // Método para obtener la casilla en la posición especificada por fila y columna en el tablero
    public JPanel obtenerCasilla(int fila, int columna) {
        // Obtiene todos los componentes (casillas) del panel del tablero
        Component[] componentes = panelTablero.getComponents();
        
        // Calcula el índice correspondiente a la fila y columna especificadas
        int indiceCasilla = fila * TAMANIO_TABLERO + columna;
        
        // Retorna la casilla (JPanel) en la posición determinada por el índice calculado
        return (JPanel) componentes[indiceCasilla];
    }
   
}