package pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class NavegadorApp extends Application {
    private WebView webView;
    private WebEngine webEngine;
    private TextField urlField;
    private Button backButton;
    private Button forwardButton;
    private Button reloadButton;
    private Button saveImageButton;
    private Connection connection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        webView = new WebView();
        webEngine = webView.getEngine();
        urlField = new TextField();

        backButton = new Button("Atrás");
        backButton.setOnAction(event -> webEngine.executeScript("history.back()"));

        forwardButton = new Button("Adelante");
        forwardButton.setOnAction(event -> webEngine.executeScript("history.forward()"));

        reloadButton = new Button("Recargar");
        reloadButton.setOnAction(event -> webEngine.reload());

        saveImageButton = new Button("Guardar Imagen");
        saveImageButton.setOnAction(event -> saveSelectedImage());

        HBox navigationBar = new HBox(backButton, forwardButton, reloadButton, saveImageButton);
        VBox vBox = new VBox(urlField, navigationBar, webView);

        Scene scene = new Scene(vBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Inicializar la base de datos SQLite
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:navegador.db");
            String createTableQuery = "CREATE TABLE IF NOT EXISTS paginas_web (id INTEGER PRIMARY KEY AUTOINCREMENT, url TEXT)";
            connection.createStatement().executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Cargar una página web predeterminada al iniciar la aplicación
        loadURL("https://www.ejemplo.com");
    }

    private void loadURL(String url) {
        webEngine.load(url);
        urlField.setText(url);

        // Guardar la página web visitada en la base de datos
        try {
            String insertQuery = "INSERT INTO paginas_web (url) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, url);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSelectedImage() {
        // Implementa la lógica para guardar la imagen seleccionada en el disco
        // Puedes usar java.awt.Image y javax.imageio.ImageIO para trabajar con imágenes
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        // Cerrar la conexión de la base de datos al salir de la aplicación
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
