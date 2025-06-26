package fr.ynov.collection;

import fr.ynov.collection.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainView view = new MainView();
        view.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
