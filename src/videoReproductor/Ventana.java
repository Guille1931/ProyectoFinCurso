package videoReproductor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class Ventana extends JFrame {
    private static final long serialVersionUID = 1L;
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JProgressBar progressBar;
    private JLabel timeLabel;
    private Timer timer;
    private boolean reproduciendo = false;
    private JPanel buttonPanel;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stopButton;
    private JButton fasterButton;
    private JButton slowerButton;
    private JButton backwardButton;
    private JPanel controlPanel;
    private JPanel mainPanel;
    private String tiempoString;
    private JPanel panel;

    public Ventana() {

    	// Configurar la ventana
        setTitle("Reproductor de Video con VLCJ | Guillermo");
        setSize(450, 350);

        // Obtener las dimensiones de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Calcular la ubicación para que la ventana esté en la esquina inferior derecha
        int x = (int) (screenSize.getWidth() - getWidth());
        int y = (int) (screenSize.getHeight() - getHeight());

        // Establecer la ubicación de la ventana
        setLocation(x, y);

        // Descubrir y cargar bibliotecas nativas de VLCJ
        new uk.co.caprica.vlcj.discovery.NativeDiscovery().discover();

        // Inicializar el componente del reproductor de medios en el EDT
        SwingUtilities.invokeLater(() -> {
            mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

            // Agregar el componente del reproductor a la ventana
            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(mediaPlayerComponent, BorderLayout.CENTER);
            add(panel, BorderLayout.CENTER);


            // Crear botones
            playButton = new JButton("Play");
            pauseButton = new JButton("Pause");
            stopButton = new JButton("Stop");
            fasterButton = new JButton("Rapido");
            slowerButton = new JButton("Lento");
            backwardButton = new JButton("Atras");

            // Agregar acciones a los botones
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reproducir();
                }
            });

            pauseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pausar();
                }
            });

            stopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    detener();
                }
            });

            fasterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ajustarVelocidad(1.5f); // Ajustar la velocidad a 1.5x
                }
            });

            slowerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ajustarVelocidad(0.5f); // Ajustar la velocidad a 0.5x
                }
            });

            backwardButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    retroceder(); // Método para retroceder la reproducción
                }
            });

            // Agregar una barra de progreso
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);

            // Agregar una etiqueta para mostrar el tiempo
            timeLabel = new JLabel("Tiempo: 0:00:00 / 0:00:00");

            // Agregar los componentes a la ventana
            buttonPanel = new JPanel();
            buttonPanel.add(backwardButton);
            buttonPanel.add(slowerButton);
            buttonPanel.add(playButton);
            buttonPanel.add(pauseButton);
            buttonPanel.add(stopButton);
            buttonPanel.add(fasterButton);

            controlPanel = new JPanel(new BorderLayout());
            // Agrega la barra de progreso al centro del panel de control
            controlPanel.add(progressBar, BorderLayout.CENTER);
            // Agrega la etiqueta de tiempo en la parte inferior del panel de control
            controlPanel.add(timeLabel, BorderLayout.SOUTH);

            mainPanel = new JPanel(new BorderLayout());
            // Agrega el panel de botones en la parte superior del panel principal
            mainPanel.add(buttonPanel, BorderLayout.NORTH);
        	// Agrega el panel de control en la parte inferior del panel principal
            mainPanel.add(controlPanel, BorderLayout.SOUTH);
            // Agrega el panel principal a la ventana en la zona sur (parte inferior)
            add(mainPanel, BorderLayout.SOUTH); // Agrega el panel principal a la ventana en la zona sur (parte inferior)

            // Hacer que el reproductor de medios comience a reproducir un video automáticamente
            mediaPlayerComponent.getMediaPlayer().playMedia("video/Curso de ajedrez de Miguel Illescas.mp4");

            // Inicializar y comenzar el temporizador
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    actualizarBarraProgresoYTiempo();
                }
            });
            timer.start();
        });

        // Configurar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                detener(); // Llama al método detener() al cerrar la ventana
            }
        });
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    // Método para restablecer velocidad
    private void velocidadNormal() {
        if (mediaPlayerComponent != null) {
            ajustarVelocidad(1.0f); // Restablecer la velocidad a 1.0x
        }
    }

    // Método para reproducir
    private void reproducir() {
        if (mediaPlayerComponent != null) {
            velocidadNormal(); // Restablecer la velocidad a 1.0x antes de reproducir
            mediaPlayerComponent.getMediaPlayer().play();
            reproduciendo = true; // Actualizar el estado de reproducción
        }
    }

    // Método para pausar
    private void pausar() {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.getMediaPlayer().pause();
            reproduciendo = false; // Actualizar el estado de reproducción
        }
    }

    // Método para detener
    private void detener() {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.getMediaPlayer().setTime(0); // Reiniciar desde el principio
            mediaPlayerComponent.getMediaPlayer().pause();
            setVisible(false); // Ocultar la ventana
        }
    }

    // Método para retroceder la reproducción
    private void retroceder() {
        if (mediaPlayerComponent != null) {
            // Obtener el tiempo actual de reproducción
            long tiempoActual = mediaPlayerComponent.getMediaPlayer().getTime();

            // Retroceder 10 segundos (ajusta según lo que necesites)
            tiempoActual -= 10000; // 10 segundos en milisegundos

            // Establecer el tiempo de reproducción
            mediaPlayerComponent.getMediaPlayer().setTime(tiempoActual);

            // Si estaba reproduciendo, continuar la reproducción desde el nuevo punto
            if (reproduciendo) {
                mediaPlayerComponent.getMediaPlayer().play();
            }
        }
    }

    // Método para ajustar la velocidad de reproducción
    private void ajustarVelocidad(float rate) {
        if (mediaPlayerComponent != null) {
            // Comprobar si se está reproduciendo y pausar antes de ajustar la velocidad
            boolean estabaReproduciendo = reproduciendo;
            if (estabaReproduciendo) {
                pausar();
            }

            mediaPlayerComponent.getMediaPlayer().setRate(rate);

            // Si estaba reproduciendo antes del cambio de velocidad, reanudar la reproducción
            if (estabaReproduciendo) {
                reproducir();
            }
        }
    }

    // Método para actualizar la barra de progreso y el tiempo
    private void actualizarBarraProgresoYTiempo() {
        if (mediaPlayerComponent != null) {
            long tiempo = mediaPlayerComponent.getMediaPlayer().getTime();
            long duracion = mediaPlayerComponent.getMediaPlayer().getLength();

            int horas = (int) (tiempo / 3600000);
            int minutos = (int) ((tiempo % 3600000) / 60000);
            int segundos = (int) ((tiempo % 60000) / 1000);

            int horasDuracion = (int) (duracion / 3600000);
            int minutosDuracion = (int) ((duracion % 3600000) / 60000);
            int segundosDuracion = (int) ((duracion % 60000) / 1000);

            int progreso = (int) ((double) tiempo / duracion * 100);
            progressBar.setValue(progreso);

            tiempoString = String.format("Tiempo: %d:%02d:%02d / %d:%02d:%02d",
                    horas, minutos, segundos,
                    horasDuracion, minutosDuracion, segundosDuracion);
            timeLabel.setText(tiempoString);
        }
    }
}
