package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import javax.xml.crypto.Data;

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

    }

    @FXML
    public void handleBack() {

    }

}
