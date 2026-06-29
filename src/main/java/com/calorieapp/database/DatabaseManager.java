package com.calorieapp.database;

import com.calorieapp.models.WeightLog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    // Create Tables \\
    public static void initializeDatabase() {
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL,\n"
                + " gender TEXT NOT NULL,\n"
                + " birth_year INTEGER NOT NULL,\n"
                + " height_inches INTEGER NOT NULL,\n"
                + " activity_level TEXT NOT NULL,\n"
                + " goal_type TEXT NOT NULL,\n"
                + " goal_weight REAL NOT NULL,\n"
                + " weekly_rate REAL NOT NULL\n"
                + ");";

        String createWeightLogs =  "CREATE TABLE IF NOT EXISTS weight_logs (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " user_id INTEGER NOT NULL,\n"
                + " weight REAL NOT NULL,\n"
                + " log_date TEXT NOT NULL,\n"
                + " FOREIGN KEY (user_id) REFERENCES users(id)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            System.out.println("Users Table Created.");
            stmt.execute(createWeightLogs);
            System.out.println("Weight Log Table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // DELETE AFTER TESTING
    private static void connect() {
        String url = "jdbc:sqlite:calorieapp.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // User Methods \\
    public static void addUser(String name, String gender, int birthYear, int heightInches,
                               String activityLevel, String goalType, double goalWeight, double weeklyRate) {
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String sql = "INSERT INTO users (name, gender, birth_year, height_inches, activity_level, goal_type, goal_weight, weekly_rate) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setInt(3, birthYear);
            pstmt.setInt(4, heightInches);
            pstmt.setString(5, activityLevel);
            pstmt.setString(6, goalType);
            pstmt.setDouble(7, goalWeight);
            pstmt.setDouble(8, weeklyRate);

            pstmt.executeUpdate();
            System.out.println("User Added: " + name);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static List<String> getAllUserNames() {
        List<String> names = new ArrayList<>();
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String sql = "SELECT name FROM users";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                names.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return names;
    }

    // Display most recent weight in user dashboard
    public static double getRecentWeight(int userId) {
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String sql = "SELECT weight FROM weight_logs WHERE user_id = ? ORDER BY log_date DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("weight");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public static int getUserIdByName(String name) {
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String sql = "SELECT id FROM users WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return -1;
    }

    // Weight Logs \\
    public static void addWeightLog(int userId, double weight, String date) {
        String DB_URL = "jdbc:sqlite:calorieapp.db";

        if (hasLogForDate(userId, date)) {
            String updateSql = "UPDATE weight_logs SET weight = ? WHERE user_id = ? AND log_date = ?";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(updateSql)) {

                pstmt.setDouble(1, weight);
                pstmt.setInt(2, userId);
                pstmt.setString(3, date);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            String insertSql = "INSERT INTO weight_logs (user_id, weight, log_date) VALUES (?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(DB_URL);
                 PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

                pstmt.setInt(1, userId);
                pstmt.setDouble(2, weight);
                pstmt.setString(3, date);
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Checks if log already exists for user on date
    public static boolean hasLogForDate(int userId, String date) {
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String sql = "SELECT id FROM weight_logs WHERE user_id = ? AND log_date = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // true if a row was found

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /*
    public ObservableList<WeightLog> getWeightLog() {
        ObservableList<WeightLog> weightLogs = FXCollections.observableArrayList();
        weightLogs.add(new WeightLog());
        return weightLogs;
    }*/

    public static List<WeightLog> getWeightLogsForUser(int userId) {
        List<WeightLog> logs = new ArrayList<>();
        String DB_URL = "jdbc:sqlite:calorieapp.db";
        String sql = "SELECT weight, log_date FROM weight_logs WHERE user_id = ? ORDER BY log_date DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                double weight = rs.getDouble("weight");
                String date = rs.getString("log_date");
                logs.add(new WeightLog(weight, date));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return logs;
    }
}
