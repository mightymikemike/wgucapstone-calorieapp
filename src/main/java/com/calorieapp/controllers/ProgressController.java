package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import com.calorieapp.database.LinearRegression;
import com.calorieapp.models.UserProfile;
import com.calorieapp.models.WeightLog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ProgressController {

    @FXML private Label titleLabel;
    @FXML private Label currentWeightLabel;
    @FXML private Label goalWeightLabel;
    @FXML private Label remainingLabel;
    @FXML private Label progressLabel;
    @FXML private Label predictionLabel;
    @FXML private ProgressBar progressBar;
    @FXML private Button backButton;

    private String currentUserName;
    private int currentUserId;

    public void setCurrentUserName(String name) {
        this.currentUserName = name;
        this.currentUserId = DatabaseManager.getUserIdByName(name);
        titleLabel.setText(currentUserName + "'s Progress");
        loadProgress();
    }

    private void loadProgress() {
        UserProfile profile = DatabaseManager.getUserProfile(currentUserId);
        double currentWeight = DatabaseManager.getRecentWeight(currentUserId);
        double goalWeight = profile.getGoalWeight();
        String goalType = profile.getGoalType();

        currentWeightLabel.setText("Current Weight: " + currentWeight + " lbs");
        goalWeightLabel.setText("Goal Weight: " + goalWeight + " lbs");

        // Remaining weight - abs for loss/gain
        double remaining = Math.abs(currentWeight - goalWeight);
        remainingLabel.setText(String.format("Remaining: %.1f lbs", remaining));

        // Calc for progress bar
        List<WeightLog> logs = DatabaseManager.getWeightLogsAscending(currentUserId);
        double startingWeight = logs.isEmpty() ? currentWeight : logs.get(0).getWeight();

        // Debug Statements
        System.out.println("Log count: " + logs.size());
        System.out.println("Starting weight: " + startingWeight);
        System.out.println("Current weight: " + currentWeight);
        System.out.println("Goal weight: " + goalWeight);
        System.out.println("Goal type: " + goalType);

        double progress;

        if (goalType.contains("Lose")) {
            double totalNeeded = startingWeight - goalWeight;
            double achieved = startingWeight - currentWeight;
            progress = totalNeeded > 0 ? achieved / totalNeeded : 0;
        } else if (goalType.contains("Gain")) {
            double totalNeeded = goalWeight - startingWeight;
            double achieved = currentWeight - startingWeight;
            progress = totalNeeded > 0 ? achieved / totalNeeded : 0;
        } else {
            progress = 1.0;
        }

        // Prevent going over 100% if user goes over goal
        progress = Math.min(1.0, Math.max(0.0, progress));

        progressBar.setProgress(progress);
        progressLabel.setText(String.format("%.1f%% to goal", progress * 100));

        // Prediction with linear regression model
        if (logs.size() >= 2) {
            LinearRegression regression = new LinearRegression();
            regression.calc(logs);

            // Predict number of days until you reach goal weight
            double slope = regression.getSlope();

            if (slope != 0) {
                double intercept = regression.predict(0);
                double daysToGoal = (goalWeight - intercept) / slope;

                if (daysToGoal > 0) {
                    // subtract previous logged days to get remaining days from most recent
                    double remainingDays = daysToGoal - (logs.size() - 1);

                    if (remainingDays > 0) {
                        LocalDate completionDate = LocalDate.now().plusDays((long) remainingDays);
                        predictionLabel.setText("Predicted completion: " + completionDate);;
                    } else {
                        predictionLabel.setText("Goal has already been reached!");
                    }
                } else {
                    predictionLabel.setText("Your progress is moving away from your goal.. Stay on track!");
                }
            } else {
                predictionLabel.setText("Not enough trend data has been collected yet..");
            }
        } else {
            predictionLabel.setText("Log more weights to see the prediction!");
        }
    }

    @FXML
    public void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/calorieapp/userprofile.fxml"));
            Parent root = loader.load();
            UserProfileController controller = loader.getController();
            controller.setUserName(currentUserName);

            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setTitle("Calorie App - " + currentUserName);
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
