package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class WelcomeController {

    @FXML
    private ListView<String> userListView;

    @FXML
    public Button addUserButton;

    @FXML
    public void initialize() {
        // Lists actual users names from db
        List<String> userNames = DatabaseManager.getAllUserNames();
        userListView.getItems().addAll(userNames);
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
