package com.calorieapp.models;

import java.time.Year;

public class UserProfile {

    private int id;
    private String name;
    private String gender;
    private int birthYear;
    private int heightInches;
    private String activityLevel;
    private String goalType;
    private double goalWeight;
    private double weeklyRate;

    public UserProfile(int id, String name, String gender, int birthYear, int heightInches,
                       String activityLevel, String goalType, double goalWeight, double weeklyRate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.heightInches = heightInches;
        this.activityLevel = activityLevel;
        this.goalType = goalType;
        this.goalWeight = goalWeight;
        this.weeklyRate = weeklyRate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public String getGoalType() {
        return goalType;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public double getWeeklyRate() {
        return weeklyRate;
    }

    public int getAge() {
        int currentYear = java.time.Year.now().getValue();
        return currentYear - birthYear;
    }

}
