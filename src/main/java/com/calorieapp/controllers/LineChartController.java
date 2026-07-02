package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import com.calorieapp.models.WeightLog;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.List;

public class LineChartController {

    @FXML private LineChart<String, Number> weightChart;
    @FXML private Label titleLabel;
    @FXML private Button backButton;

    private String currentUserName;
    private int currentUserId;

    public void setCurrentUserName(String name) {
        this.currentUserName = name;
        this.currentUserId = DatabaseManager.getUserIdByName(name);
        titleLabel.setText(currentUserName + "'s Weight Chart");
        loadChart();
    }

    private void loadChart() {
        weightChart.getData().clear(); // Clears any existing data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weight (lbs)");

        List<WeightLog> logs = DatabaseManager.getWeightLogsAscending(currentUserId);

        for (WeightLog log : logs) {
            series.getData().add(new XYChart.Data<>(log.getDate(), log.getWeight()));
        }

        weightChart.getData().add(series);
    }

}
