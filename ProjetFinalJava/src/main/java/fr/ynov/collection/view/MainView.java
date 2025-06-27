package fr.ynov.collection.view;

import fr.ynov.collection.presenter.MainPresenter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
            stage.setTitle("Gestionnaire de Collection de Jeux Vidéo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load jeux-view.fxml", e);
        }
    }
}
