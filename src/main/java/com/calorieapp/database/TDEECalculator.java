package com.calorieapp.database;

import com.calorieapp.models.UserProfile;

public class TDEECalculator {

    public static double calculateDailyCalories(UserProfile user) {
        double weightLbs = DatabaseManager.getRecentWeight(user.getId());
        String activityLevel = user.getActivityLevel();
        double weeklyRate = user.getWeeklyRate();
        String goalType = user.getGoalType();
        int heightInches = user.getHeightInches();
        int age = user.getAge();
        String gender = user.getGender();

        double weightKg = weightLbs / 2.205;
        double heightCm = heightInches * 2.54;

        double bmr;
        double multiplier;



        if (gender.equals("Male")) {
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
        } else {
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age -161;
        }

        if (activityLevel.contains("Sedentary")) {
            multiplier = 1.2;
        } else if (activityLevel.contains("Light")) {
            multiplier = 1.375;
        } else if (activityLevel.contains("Moderate")) {
            multiplier = 1.55;
        } else if (activityLevel.contains("Heavy")) {
            multiplier = 1.725;
        } else {
            multiplier = 1.9;
        }

        double tdee = bmr * multiplier;
        double calories;

        // 3500 calories a week (equal to losing/gaining about 1lb per week) / 7 days a week = 500
        if (goalType.contains("Lose")) {
            calories = tdee - weeklyRate * 500.0;
        } else if (goalType.contains("Gain")) {
            calories = tdee + weeklyRate * 500.0;
        } else {
            calories = tdee;
        }

        return calories;
    }
}
