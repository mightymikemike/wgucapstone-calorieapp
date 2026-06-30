package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import com.calorieapp.controllers.LogWeightController;
import com.calorieapp.database.LinearRegression;
import com.calorieapp.database.TDEECalculator;
import com.calorieapp.models.UserProfile;
import com.calorieapp.models.WeightLog;
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
    @FXML private Button logWeightButton;

    private String currentUserName = "User";
    private double currentWeight = -1;
    private int recommendedCalories = 2500; // Still a placeholder

    @FXML
    public void initialize() {
        updateLabels();
    }

    public void setUserName(String name) {
        this.currentUserName = name;
        int userId = DatabaseManager.getUserIdByName(name);
        this.currentWeight = DatabaseManager.getRecentWeight(userId);
        UserProfile profile = DatabaseManager.getUserProfile(userId);

        // Base recommendation
        double baseRec = TDEECalculator.calculateDailyCalories(profile);

        // Get weight history for regression model
        List<WeightLog> logs = DatabaseManager.getWeightLogsForUser(userId);

        // Create regression model
        LinearRegression regression = new LinearRegression();
        regression.calc(logs);
        double adjustedCals = regression.adjustCalories(baseRec, profile.getWeeklyRate());

        this.recommendedCalories = (int) adjustedCals;

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
    public void handleEditUser() {
    //REVISIT
    }

    @FXML
    public void handleDeleteUser() {
    //REVISIT
    }

    @FXML
    public void handleViewLogs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/calorieapp/weightlog.fxml"));
            Parent root = loader.load();
            WeightLogController controller = loader.getController();
            controller.setUserName(currentUserName);

            Stage stage = (Stage) changeUserButton.getScene().getWindow();
            stage.setTitle(currentUserName + "'s Weight Log");
            stage.setScene(new Scene(root, 800, 650));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
