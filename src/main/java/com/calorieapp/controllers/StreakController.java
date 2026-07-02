package com.calorieapp.controllers;

import com.calorieapp.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class StreakController {

    @FXML private Label titleLabel;
    @FXML private Label monthLabel;
    @FXML private GridPane calendarGrid;
    @FXML private Button backButton;

    private String currentUserName;
    private int currentUserId;
    private YearMonth displayedMonth;
    private LocalDate startDate;

    public void setCurrentUserName(String name) {
        this.currentUserName = name;
        this.currentUserId = DatabaseManager.getUserIdByName(name);
        this.displayedMonth = YearMonth.now();

        titleLabel.setText(currentUserName + "'s Streak Calendar");
        buildCalendar();
    }

    public void buildCalendar() {
        calendarGrid.getChildren().clear();

        //Update month label
        String monthName = displayedMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        monthLabel.setText(monthName + " " + displayedMonth.getYear());

        // Get logged dates for month
        List<String> loggedDates = DatabaseManager.getLoggedDatesForMonth(currentUserId, displayedMonth.getYear(), displayedMonth.getMonthValue());

        // Headers for day of week
        int i;
        String[] dayHeaders = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (i = 0; i < 7; i++) {
            Label header = new Label(dayHeaders[i]);
            header.setStyle("-fx-font-weight: bold; -fx-min-width: 45px; -fx-alignment: CENTER;");
            calendarGrid.add(header, i, 0);
        }

        // Locate first of the month
        LocalDate firstOfMonth = displayedMonth.atDay(1);
        int startCol = firstOfMonth.getDayOfWeek().getValue() % 7; // Sets sunday as 0 instead of 7

        int daysInMonth = displayedMonth.lengthOfMonth();
        int col = startCol;
        int row = 1;

        for (int day = 1; day <= daysInMonth; day++) {
            String dateStr = String.format("%d-%02d-%02d", displayedMonth.getYear(), displayedMonth.getMonthValue(), day);

            boolean hasLog = loggedDates.contains(dateStr);

            Label dayLabel = new Label(String.valueOf(day));
            dayLabel.setStyle("-fx-min-width: 45px; -fx-min-height: 45px; " + "-fx-alignment: CENTER; -fx-background-radius: 5px; " +
                    (hasLog ? "-fx-background-color: #27ae60; -fx-text-fill: white;" : "-fx-background-color: #ff0000; -fx-text-fill: #ffffff;"));

            StackPane cell = new StackPane(dayLabel);
            calendarGrid.add(cell, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }

        }
    }

    @FXML
    public void handlePrevMonth() {
        displayedMonth = displayedMonth.minusMonths(1);
        buildCalendar();
    }

    @FXML
    public void handleNextMonth() {
        displayedMonth = displayedMonth.plusMonths(1);
        buildCalendar();
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
