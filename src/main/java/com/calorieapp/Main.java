package com.calorieapp;

import com.calorieapp.database.DatabaseManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import javafx.scene.chart.LineChart;

public class Main extends Application {

    //Button button;
    //Scene scene1, scene2;

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