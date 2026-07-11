package com.calorieapp;

import com.calorieapp.database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseManager.initializeDatabase();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/calorieapp/welcome.fxml")
        );
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setTitle("Calorie App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}