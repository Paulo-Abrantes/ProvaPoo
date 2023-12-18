package application;

import java.io.IOException;
import javafx.
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main{
    @Override
    public void start(Stage stagePrincipal) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ProjetoPOO.fxml"));
            Scene scene = new Scene(root);
            stagePrincipal.setScene(scene);
            stagePrincipal.setTitle("Apicultura");
            stagePrincipal.show();
        } catch (Exception e) {
            // Desembrulha a exceção e imprime detalhes
            Throwable cause = e.getCause();
            if (cause != null) {
                System.err.println("Exceção raiz: " + cause);
                cause.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
    }
}
