package ajedrez;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class Ventana extends JFrame {
    private static final long serialVersionUID = 1L;
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private JProgressBar progressBar;
    private JLabel timeLabel;
    private Timer timer;

    public Ventana() {
        // Configurar la ventana
        setTitle("Reproductor de Video con VLCJ | Guillermo");
        setSize(400, 300);

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
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(mediaPlayerComponent, BorderLayout.CENTER);
            add(panel, BorderLayout.CENTER);

            // Crear botones
            JButton playButton = new JButton("Play");
            JButton pauseButton = new JButton("Pause");
            JButton stopButton = new JButton("Stop");
            JButton fasterButton = new JButton("Rapido 1.5x");
            JButton slowerButton = new JButton("Lento 0.5x");

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

            // Agregar una barra de progreso
            progressBar = new JProgressBar(0, 100);
            progressBar.setStringPainted(true);

            // Agregar una etiqueta para mostrar el tiempo
            timeLabel = new JLabel("Tiempo: 0:00:00 / 0:00:00");

            // Agregar los componentes a la ventana
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(slowerButton);
            buttonPanel.add(playButton);
            buttonPanel.add(pauseButton);
            buttonPanel.add(stopButton);
            buttonPanel.add(fasterButton);

            JPanel controlPanel = new JPanel(new BorderLayout());
            controlPanel.add(progressBar, BorderLayout.CENTER);
            controlPanel.add(timeLabel, BorderLayout.SOUTH);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(buttonPanel, BorderLayout.NORTH);
            mainPanel.add(controlPanel, BorderLayout.SOUTH);

            add(mainPanel, BorderLayout.SOUTH);

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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Método para reproducir
    private void reproducir() {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.getMediaPlayer().play();
        }
    }

    // Método para pausar
    private void pausar() {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.getMediaPlayer().pause();
        }
    }

    // Método para detener
    private void detener() {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.getMediaPlayer().stop();
        }
    }

    // Método para ajustar la velocidad de reproducción
    private void ajustarVelocidad(float rate) {
        if (mediaPlayerComponent != null) {
            mediaPlayerComponent.getMediaPlayer().setRate(rate);
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

            String tiempoString = String.format("Tiempo: %d:%02d:%02d / %d:%02d:%02d",
                    horas, minutos, segundos,
                    horasDuracion, minutosDuracion, segundosDuracion);
            timeLabel.setText(tiempoString);
        }
    }
}
