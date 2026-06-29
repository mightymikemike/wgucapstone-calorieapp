package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.awt.*;

public class LogWeightController {

    @FXML private DatePicker datePicker;
    @FXML private TextField weightField;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;

    private String currentUserName;
    private int currentUserId;

    @FXML
    public void initialize() {
        datePicker.setValue(LocalDate.now());
    }

    public void setUserName (String name) {
        this.currentUserName = name;
        this.currentUserId = DatabaseManager.getUserIdByName(name);
    }

    @FXML
    public void handleSave() {
        errorLabel.setText("");

        if (weightField.getText().isEmpty() || datePicker.getValue() == null) {
            errorLabel.setText("Please enter a weight and date");
            return;
        }

        double weight;
        try {
            weight = Double.parseDouble(weightField.getText());
        } catch (Exception e) {
            errorLabel.setText("Weight must be number");
            return;
        }

        String date = datePicker.getValue().toString();

        DatabaseManager.addWeightLog(currentUserId, weight, date);

        // Close popup
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

}
