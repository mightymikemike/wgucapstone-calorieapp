package com.calorieapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class WelcomeController {

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

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add New User");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleUserSelect() throws Exception {
        String selectedUser = userListView.getSelectionModel().getSelectedItem();

        // Ignore clicks not on user
        if (selectedUser == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/calorieapp/userprofile.fxml")
        );
        Parent root = loader.load();

        // Grab controller instance for passing data
        UserProfileController controller = loader.getController();
        controller.setUserName(selectedUser);

        Stage stage = (Stage) userListView.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Calorie App - " + selectedUser);
        stage.setScene(scene);
        stage.show();
    }


}
