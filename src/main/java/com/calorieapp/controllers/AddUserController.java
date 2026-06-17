package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Year;

public class AddUserController {

    @FXML private TextField nameField;
    @FXML private ComboBox<String> genderBox;
    @FXML private TextField yearField;
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
        // Adjust for specific error
        if (nameField.getText().isEmpty() ||
                genderBox.getValue() == null ||
                yearField.getText().isEmpty() ||
                feetField.getText().isEmpty() ||
                inchesField.getText().isEmpty() ||
                weightField.getText().isEmpty() ||
                goalWeightField.getText().isEmpty() ||
                goalBox.getValue() == null ||
                rateBox.getValue() == null ||
                activityBox.getValue() == null) {

            errorLabel.setText("Please fill out all fields.");
            return;
        }

        // Validate numeric fields
        try {
            int year = Integer.parseInt(yearField.getText());
            int age = getAge(year);
            int feet = Integer.parseInt(feetField.getText());
            int inches = Integer.parseInt(inchesField.getText());
            double weight = Double.parseDouble(weightField.getText());
            double goalWeight = Double.parseDouble(goalWeightField.getText());

            // Convert height to inches
            int totalInches = (feet * 12) + inches;

            // Save to database
            /*
            System.out.println("User: " + nameField.getText());
            System.out.println("Age: " + age);
            System.out.println("Height: " + totalInches + " inches");
            System.out.println("Weight: " + weight + " lbs");
            System.out.println("Goal: " + goalWeight + "lbs");
             */
            DatabaseManager.addUser(
                    nameField.getText(),
                    genderBox.getValue(),
                    year,
                    totalInches,
                    activityBox.getValue(),
                    goalBox.getValue(),
                    goalWeight,
                    Double.parseDouble(rateBox.getValue().replaceAll("[^0-9.]", ""))
            );

            System.out.println("User " + nameField.getText() + " added successfully!");

            // Return to welcome screen
            goToWelcome();

        } catch (NumberFormatException e) {
            errorLabel.setText("Age, height, and weight must be numbers.");
        }
    }

    public int getAge(int year) {
        int currentYear = Year.now().getValue();
        return currentYear - year;
    }

    @FXML
    public void handleCancel() throws Exception {
        goToWelcome();
    }

    private void goToWelcome() throws RuntimeException {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/com/calorieapp/welcome.fxml")
            );
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.setTitle("Calorie App");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
