package com.calorieapp.models;

public class WeightLog {

    private double weight;
    private String date;

    public WeightLog(double weight, String date) {
        this.weight = weight;
        this.date = date;
    }

    public double getWeight() {
        return weight;
    }

    public String getDate() {
        return date;
    }


}
