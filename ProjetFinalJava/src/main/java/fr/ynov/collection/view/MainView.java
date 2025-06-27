package fr.ynov.collection.view;

import fr.ynov.collection.presenter.MainPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;

public class MainView {

    private final MainPresenter presenter;

    public MainView() {
        this.presenter = new MainPresenter();
    }

    public void show(Stage stage) {
        try {
            presenter.initialize();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jeux-view.fxml"));
            Parent root = loader.load();

            // Ajouter les styles BootstrapFX
            Scene scene = new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

            stage.setTitle("Gestionnaire de Collection de Jeux Vidéo");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load jeux-view.fxml", e);
        }
    }
}
