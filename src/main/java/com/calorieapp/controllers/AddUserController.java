package com.calorieapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddUserController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> genderBox;
    @FXML private TextField ageField;
    @FXML private TextField feetField;
    @FXML private TextField inchesField;
    @FXML private TextField weightField;
    @FXML private TextField goalWeightField;
    @FXML private ComboBox<String> goalBox;
    @FXML private ComboBox<String> rateBox;
    @FXML private ComboBox<String> activityBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        genderBox.getItems().addAll("Male", "Female");
        goalBox.getItems().addAll("Lose Weight", "Gain Weight", "Maintain");

        rateBox.getItems().addAll(
                "0.5 lbs per week",
                "1.0 lbs per week",
                "1.5 lbs per week",
                "2.0 lbs per week",
                "2.5 lbs per week"
        );

        activityBox.getItems().addAll(
                "Sedentary - Office/non-active Job",
                "Light Exercise - 1-3 days/week",
                "Moderate Exercise - 3-5 days/week",
                "Heavy Exercise - 5-7 days/week",
                "Athlete - 2x per day"
        );
    }

    @FXML
    public void handleSave() {
        // Clear previous errors
        errorLabel.setText("");

        // Validate fields are filled

    }

}
