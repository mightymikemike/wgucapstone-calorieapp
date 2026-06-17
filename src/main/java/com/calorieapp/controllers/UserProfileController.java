package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class UserProfileController {

    @FXML private Label titleLabel;
    @FXML private Label weightLabel;
    @FXML private Label calorieLabel;
    @FXML private Button changeUserButton;

    private String currentUserName = "User";
    private double currentWeight = -1;
    private int recommendedCalories = 2500; // Still a placeholder

    @FXML
    public void initialize() {
        //List<String> userNames = DatabaseManager.getAllUserNames();
        //userListView.getItems().addAll(userNames);
        updateLabels();
    }

    public void setUserName(String name) {
        this.currentUserName = name;

        int userId = DatabaseManager.getUserIdByName(name);
        this.currentWeight = DatabaseManager.getRecentWeight(userId);
        // Add Calorie Recommendation
        updateLabels();
    }

    @FXML
    public void updateLabels() {
        titleLabel.setText("Current User - " + currentUserName);

        if (currentWeight == -1) {
            weightLabel.setText("Current Weight: No weight has been logged yet");
        } else {
            weightLabel.setText("Current Weight: " + currentWeight + " lbs");
        }

        calorieLabel.setText("Recommended Calories: " + recommendedCalories + " cal/day");
    }

    @FXML
    public void handleChangeUser() {
        navigateTo("/com/calorieapp/welcome.fxml", "Calorie App", 800, 600);
    }

    @FXML
    public void handleViewLogs() {
        navigateTo("/com/calorieapp/weightlog.fxml", "Weight Logs", 800, 600);
    }

    @FXML
    public void handleLogWeight() {
        // Placeholder
        System.out.println("Log Weight Clicked - Screen Unavailable");
    }

    @FXML
    public void handleOpenGraph() {
        // Placeholder
        System.out.println("Weight Graph Clicked - Screen Unavailable");
    }

    @FXML
    public void handleOpenStreak() {
        // Placeholder
        System.out.println("Streak Calendar Clicked - Screen Unavailable");
    }

    @FXML
    public void handleOpenProgress() {
        // Placeholder
        System.out.println("Progress Indicator Clicked - Screen Unavailable");
    }

    private void navigateTo(String fxmlPath, String title, int width, int height) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) changeUserButton.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
