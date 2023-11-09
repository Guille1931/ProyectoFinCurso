package ajedrez;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.*;
import java.awt.Toolkit;

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
    private JPanel panelOpciones;  // Panel que contiene los botones de opciones de color del tablero
    private JPanel panelOpciones_1;
    private JButton reiniciarButton;  // Botón para reiniciar la partida
    private List<String> movimientosGuardados = new ArrayList<>();  // Lista para almacenar los movimientos realizados
    private JLabel etiquetaNombreJugador1;  // Etiqueta para mostrar el nombre del Jugador 1 (Blancas)
    private JLabel etiquetaNombreJugador2;  // Etiqueta para mostrar el nombre del Jugador 2 (Negras)
    private JLabel etiquetaCronometro;  // Etiqueta para mostrar el tiempo transcurrido
    private Timer timer;  // Temporizador para llevar el control del tiempo
    private int segundosTranscurridos;  // Variable para almacenar los segundos transcurridos en el cronómetro
    private JPanel panelOpcionesYCronometro;  // Panel que contiene las opciones y el cronómetro
    private JLabel lblNewLabel;

    
    // Constructor de la clase ChessBoardGUI que inicializa la interfaz gráfica del tablero de ajedrez
    public ChessBoardGUI() {   	
    	// Crear un cuadro de diálogo para solicitar el nombre del jugador 1
        String nombreJugador1 = JOptionPane.showInputDialog(null, "Ingrese nombre del Jugador con Blancas:", "Juega con Blancas", JOptionPane.PLAIN_MESSAGE);

        // Crear un cuadro de diálogo para solicitar el nombre del jugador 2
        String nombreJugador2 = JOptionPane.showInputDialog(null, "Ingrese nombre del Jugador con Negras:", "Juega con Negras", JOptionPane.PLAIN_MESSAGE);

        // Verificar si se ingresaron nombres válidos, si no, establecer nombres predeterminados
        if (nombreJugador1 == null || nombreJugador1.trim().isEmpty()) {
            nombreJugador1 = "Jugador Blancas";
        }
        if (nombreJugador2 == null || nombreJugador2.trim().isEmpty()) {
            nombreJugador2 = "Jugador Negras";
        }
       
        // Crear las etiquetas con los nombres de los jugadores
        etiquetaNombreJugador1 = new JLabel(nombreJugador1);
        etiquetaNombreJugador2 = new JLabel(nombreJugador2);

        // Establecer el ancho fijo y dejar que la altura se ajuste automáticamente
        etiquetaNombreJugador1.setPreferredSize(new Dimension(150, etiquetaNombreJugador1.getPreferredSize().height));
        etiquetaNombreJugador2.setPreferredSize(new Dimension(150, etiquetaNombreJugador2.getPreferredSize().height));

        // Establecer el texto para que esté centrado horizontal y verticalmente
        etiquetaNombreJugador1.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaNombreJugador1.setVerticalAlignment(SwingConstants.CENTER);
        etiquetaNombreJugador2.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaNombreJugador2.setVerticalAlignment(SwingConstants.CENTER);

        // Establecer el tamaño de fuente de las etiquetas
        etiquetaNombreJugador1.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 22));
        etiquetaNombreJugador2.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 22));

        // Crear un panel para contener las etiquetas de los nombres de los jugadores
        panelOpciones = new JPanel(new GridLayout(2, 1));
        panelOpciones.setBounds(0, 250, 300, 142);
        panelOpciones.add(etiquetaNombreJugador1);
        panelOpciones.add(etiquetaNombreJugador2);  
     
    	// Crear un nuevo JFrame con el título "Tablero de Ajedrez"
        marco = new JFrame("Tablero de Ajedrez");
        marco.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\bfc19\\Documents\\GitHub\\ProyectoFinCurso\\ProyectoFinCurso\\imagenes\\logo"));
        // Establecer la operación de cierre del JFrame al cerrar la ventana
        marco.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Establecer el diseño del JFrame como BorderLayout
        marco.getContentPane().setLayout(new BorderLayout());

        // Crear un nuevo panel con un GridLayout para representar el tablero de ajedrez
        panelTablero = new JPanel(new GridLayout(TAMANIO_TABLERO, TAMANIO_TABLERO));
        
        // Inicializar el modelo de la lista de movimientos y añadir encabezados
        listaMovimientosModelo = new DefaultListModel<>();
        JLabel encabezado1 = new JLabel("<html><center><font color='blue'><b>Negras</b></font></center></html>");
        JLabel encabezado = new JLabel("<html><center><font color='red'><b>HISTORIAL MOVIMIENTOS</b></font></center></html>");
        JLabel encabezado2 = new JLabel("<html><center><font color='blue'><b>Blancas</b></font></center></html>");

        listaMovimientosModelo.addElement(encabezado1.getText());
        listaMovimientosModelo.addElement(encabezado.getText());
        listaMovimientosModelo.addElement(encabezado2.getText());
        
        // Crear un JList personalizado para mostrar los encabezados en dos columnas
        JList<String> customJList = new JList<>(listaMovimientosModelo);
        customJList.setLayoutOrientation(JList.HORIZONTAL_WRAP); // Muestra elementos en varias filas y columnas

        // Establecer el cellRenderer para personalizar la apariencia de los elementos en la lista
        customJList.setCellRenderer(new DefaultListCellRenderer() {
         
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setForeground(Color.RED); // Establece el color del texto a rojo
                label.setFont(label.getFont().deriveFont(Font.BOLD)); // Establece el texto en negrita
                return label;
            }
        });

        // Establecer el tamaño y agregar la lista de movimientos al JScrollPane
        JScrollPane scrollPane = new JScrollPane(customJList);
        scrollPane.setPreferredSize(new Dimension(250, 300)); // Establece el ancho y alto deseados

        // Agregar el JScrollPane al JFrame en el centro
        marco.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Establecer la celda renderer para personalizar la apariencia de los elementos en la lista
        listaMovimientos = new JList<>(listaMovimientosModelo);
     
        // Configurar el tamaño de la lista de movimientos
        listaMovimientos.setVisibleRowCount(10); // Establece el número de filas visibles
        listaMovimientos.setFixedCellWidth(250); // Establece el ancho fijo de las celdas
     
        // Inicializar el mapa que almacena las posiciones de las piezas en el tablero
        posicionesPiezas = new HashMap<>();

        // Crear una etiqueta para mostrar el turno actual
        etiquetaTurno = new JLabel("Turno de Blancas");
        etiquetaTurno.setFont(new Font("Rockwell Extra Bold", Font.PLAIN, 22));

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
        
        etiquetaCronometro = new JLabel();        
        etiquetaCronometro.setBounds(90, 0, 140, 30);
        etiquetaCronometro.setHorizontalAlignment(SwingConstants.CENTER); // Alinear el texto al centro horizontalmente
        etiquetaCronometro.setVerticalAlignment(SwingConstants.CENTER); // Alinear el texto al centro verticalmente
        etiquetaCronometro.setFont(new Font("DS-Digital", Font.PLAIN, 30)); // Establecer el tamaño de fuente
        etiquetaCronometro.setForeground(Color.WHITE); // Establecer el color del texto a blanco
        etiquetaCronometro.setBackground(Color.BLACK); // Establecer el color de fondo a negro
        etiquetaCronometro.setOpaque(true); // Permitir que el fondo del JLabel sea visible
        
        // Establecer el padding alrededor del texto (por ejemplo, 10 píxeles en todos los lados)
        etiquetaCronometro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Inicializar el temporizador para actualizar el cronómetro cada segundo
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundosTranscurridos++;
                actualizarCronometro();
            }
        });                   
        timer.start(); // Iniciar el temporizador
        
        // Crear un panel para contener las etiquetas de opciones y el cronómetro
        panelOpcionesYCronometro = new JPanel();
        panelOpcionesYCronometro.setLayout(null);

        // Agregar las etiquetas al panel
        panelOpcionesYCronometro.add(etiquetaCronometro);
        panelOpcionesYCronometro.add(panelOpciones);
        
        // Establecer la alineación horizontal y vertical para centrar el contenido
        panelOpcionesYCronometro.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar horizontalmente
        panelOpcionesYCronometro.setAlignmentY(Component.CENTER_ALIGNMENT); // Centrar verticalmente

        // Establecer el ancho deseado para el panelOpcionesYCronometro
        int anchoPanelOpcionesYCronometro = 300; // Cambia el valor según tus necesidades
        panelOpcionesYCronometro.setPreferredSize(new Dimension(anchoPanelOpcionesYCronometro, marco.getHeight()));

        // Agregar el panelOpcionesYCronometro en el lado oeste del marco
        marco.getContentPane().add(panelOpcionesYCronometro, BorderLayout.WEST);

        JPanel panel = new JPanel();
       
        marco.getContentPane().add(panel);
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.pack();
        marco.setVisible(true);    

        panelOpciones_1 = new JPanel();
        panelOpciones_1.add(new JLabel("Color del tablero: "));
        panelOpciones_1.add(blancoButton);
        panelOpciones_1.add(grisButton);
       
        // Agregar el panel de opciones en la parte superior del JFrame usando BorderLayout
        marco.getContentPane().add(panelOpciones_1, BorderLayout.NORTH);
        
        // Crear un botón para reiniciar la aplicación
        reiniciarButton = new JButton("Reiniciar partida");
        panelOpciones_1.add(reiniciarButton);
        reiniciarButton.setEnabled(false); // Inicializar como deshabilitado
        
        lblNewLabel = new JLabel("New label");
        panelOpciones_1.add(lblNewLabel);
        
        reiniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Invocar el método para reiniciar la aplicación
                reiniciarAplicacion();
            }
        });      
        
        // Inicializar el tablero y configurar la interfaz gráfica
        inicializarTablero();
        configurarInterfaz();
        // Actualizar el label del turno para mostrar el turno inicial
        actualizarLabelTurno();
    }   

    public static void reiniciarAplicacion() {
        try {
            String comandoJava = System.getProperty("java.home") + "/bin/java";
            String classpath = System.getProperty("java.class.path");
            String nombreClase = "ajedrez.Main"; // Nombre completo de la clase principal

            ProcessBuilder constructorProceso = new ProcessBuilder(comandoJava, "-cp", classpath, nombreClase);
            constructorProceso.start();
            System.exit(0); // Termina el proceso actual
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    public String obtenerNombrePiezaDesdeCasilla(JPanel casilla) {
        // Verifica si la casilla tiene una pieza
        if (posicionesPiezas.containsKey(casilla)) {
            return posicionesPiezas.get(casilla);
        }
        return null; // Retorna null si la casilla está vacía
    }
    
    private void actualizarCronometro() {
        int horas = segundosTranscurridos / 3600;
        int minutos = (segundosTranscurridos % 3600) / 60;
        int segundos = segundosTranscurridos % 60;
        String tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        etiquetaCronometro.setText(tiempoFormateado);
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
        marco.getContentPane().add(panelTablero, BorderLayout.CENTER);
        // Agregar una barra de desplazamiento a la lista de movimientos y colocarla en el lado derecho del marco
        marco.getContentPane().add(new JScrollPane(listaMovimientos), BorderLayout.EAST);
        // Agregar el panel de botones en la parte inferior del marco usando BorderLayout
        marco.getContentPane().add(panelBotones, BorderLayout.SOUTH);
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
        @Override
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
       
        // Verifica si la pieza capturada es un rey
        if (PiezaCapturada != null && PiezaCapturada.startsWith("rey")) {
        	// Establecer el idioma deseado (por ejemplo, español)
        	Locale spanishLocale = Locale.forLanguageTag("es-ES");
        	// Configurar el idioma para JOptionPane
            JOptionPane.setDefaultLocale(spanishLocale);
            // Definir los textos personalizados para los botones
            Object[] opciones = {"Sí", "No"};
            // Detener el temporizador
            timer.stop();
            // La partida ha finalizado, muestra un mensaje y marca la partida como finalizada
            int respuesta = JOptionPane.showOptionDialog(null, "¡Ganador!" + (turnoBlancas ? "Negras" : "Blancas") , "Quieres volver a jugar?",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            // Comprobar la respuesta del usuario
            if (respuesta == JOptionPane.YES_OPTION) {
                reiniciarButton.setEnabled(true);
                String mensajeFinal = "GANADOR: - " + (turnoBlancas ? "Negras" : "Blancas");
                
                listaMovimientosModelo.addElement(mensajeFinal);// Agregar el mensaje "PARTIDA FINALIZADA" a la lista de movimientos guardados
                movimientosGuardados.add(mensajeFinal);
                // Registra el movimiento en el archivo de texto
                Registro.registrarMovimiento(mensajeFinal);
                // Guarda el movimiento en la base de datos
                BaseDeDatos.guardarMovimientoEnBaseDeDatos(mensajeFinal);
                return;
            } else {
                marco.dispose();
                String mensajeFinal = "GANADOR: - " + (turnoBlancas ? "Negras" : "Blancas");
                // Agregar el mensaje "PARTIDA FINALIZADA" a la lista de movimientos guardados
                movimientosGuardados.add(mensajeFinal);
                // Registra el movimiento en el archivo de texto
                Registro.registrarMovimiento(mensajeFinal);
                // Guarda el movimiento en la base de datos
                BaseDeDatos.guardarMovimientoEnBaseDeDatos(mensajeFinal);
            }
        }
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
            etiquetaTurno.setText("Turno de " + (turnoBlancas ? "Blancas" : "Negras"));
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