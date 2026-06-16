package com.calorieapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//import javafx.scene.chart.LineChart;

public class Main extends Application {

    Button button;

    Stage window;
    Scene scene1, scene2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        Label label1 = new Label("Welcome to the window");

        // Button 1
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        //Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 800, 800);

        // Button 2
        Button button2 = new Button("Go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        // Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 800, 800);

        window.setScene(scene1); // Sets initial scene

        // Title
        window.setTitle("Calorie App");
        window.show();


        /*
        window.setTitle("Calorie App");
        button = new Button("Click Me");
        //button.setText("Button Text")

        // Triggers event on button click
        button.setOnAction(e -> handleButtonClick(this));
        button.setOnAction(e -> {
            System.out.println("Lets go");
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
        */
    }

    @FXML
    public void handleButtonClick(Main event) {
        System.out.println("Button Clicked!");
    }
}

// https://www.youtube.com/watch?v=FLkOX4Eez6o