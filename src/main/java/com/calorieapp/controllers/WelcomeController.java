package com.calorieapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class WelcomeController {

    //Stage stage = new Stage();

    @FXML
    private ListView<String> userListView;

    @FXML
    public Button addUserButton;

    @FXML
    public void initialize() {
        //placeholder
        userListView.getItems().addAll("User 1", "User 2", "User 3");
    }

    @FXML
    public void handleAddUser() throws Exception {
        Stage stage = (Stage) addUserButton.getScene().getWindow();

        //bring to add user window
        Parent root = FXMLLoader.load(
                getClass().getResource("/com/calorieapp/adduser.fxml")
        );

        Scene scene = new Scene(root, 400, 600);
        stage.setTitle("Add New User");
        stage.setScene(scene);
        stage.show();
    }


}
