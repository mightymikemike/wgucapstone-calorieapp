package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import com.calorieapp.models.WeightLog;
import com.calorieapp.controllers.LogWeightController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.List;

public class WeightLogController {

    @FXML private ListView<WeightLog> weightLogView;
    @FXML private Label titleLabel;
    @FXML private Label weightLabel;
    @FXML private Label calorieLabel;
    @FXML private Button returnToProfileButton;
    @FXML private Button logWeightButton;

    private String currentUserName;
    private int currentUserId;
    private double currentWeight;
    private int recommendedCalories = 2500; // Still a placeholder

    @FXML
    public void initialize() {
        updateLabels();
    }

    @FXML
    public void updateLabels() {
        titleLabel.setText(currentUserName + "'s Weight Log");
        weightLabel.setText("Current Weight: " + currentWeight + " lbs");
        calorieLabel.setText("Recommended Calories: " + recommendedCalories + " cal/day");
    }

    public void setCurrentUserName (String name) {
        this.currentUserName = name;
        this.currentUserId = DatabaseManager.getUserIdByName(name);
        // ADd calorie recommendation change

        loadWeightLogs();
        updateLabels();
    }

    private void loadWeightLogs() {
        weightLogView.getItems().clear();
        List<WeightLog> logs = DatabaseManager.getWeightLogsForUser(currentUserId);
        weightLogView.getItems().addAll(logs);
    }

    @FXML
    public void handleLogWeight() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/calorieapp/logweight.fxml"));
            Parent root = loader.load();
            LogWeightController controller = loader.getController();
            controller.setUserName(currentUserName);

            Stage popupStage = new Stage();
            popupStage.setTitle("Log Weight");
            popupStage.setScene(new Scene(root, 300, 200));
            popupStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleReturnToProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/calorieapp/userprofile.fxml"));
            Parent root = loader.load();
            UserProfileController controller = loader.getController();
            controller.setUserName(currentUserName);

            Stage stage = (Stage) returnToProfileButton.getScene().getWindow();
            stage.setTitle("Current User - " + currentUserName);
            stage.setScene(new Scene(root, 800, 650));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateTo(String fxmlPath, String title, int width, int height) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) returnToProfileButton.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add refresh button since youhave to back out to user profile before weight populates on weight log screen

}
