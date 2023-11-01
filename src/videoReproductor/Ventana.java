package videoReproductor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class Ventana extends JFrame {
    private static final long serialVersionUID = 1L;
    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public Ventana() {
        setTitle("Reproductor de Video con VLCJ | Guillermo");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Inicializar el componente del reproductor de medios
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        // Agregar el componente del reproductor a la ventana
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(mediaPlayerComponent, BorderLayout.CENTER);
        add(panel);

        // Hacer que el reproductor de medios comience a reproducir un video
        mediaPlayerComponent.getMediaPlayer().playMedia("C:\\Users\\bfc19\\Desktop\\Repositorio de pruebas\\ProyectoFinCurso\\video\\Agua.AVI");
    }

}
