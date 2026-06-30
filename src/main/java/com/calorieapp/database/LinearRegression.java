package com.calorieapp.database;

import com.calorieapp.models.WeightLog;

import java.util.List;

public class LinearRegression {
    private double m; // Slope = rate of weight change per day
    private double b; // Intercept = starting point of line

    public void calc(List<WeightLog> logs) {
        double sumX = 0;
        double sumY = 0;
        double sumXY = 0;
        double sumX2 = 0;
        int n = logs.size();

        // Checks if there are 1 or less log entries
        if (n < 2) {
            m = 0;
            b = logs.isEmpty() ? 0 : logs.get(0).getWeight(); // Check ternary condition
            return;
        }

        for (int i = 0; i < n; i++) {
            double x = i;
            double y = logs.get(i).getWeight();

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        m = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        b = (sumY - m * sumX) / n;

    }

    // Predict weight for future day
    public double predict(double x) {
        return m * x + b;
    }

    // Adjust recommendation based on current recommendation + users goal
    public double adjustCalories(double currentCalories, double weeklyRateGoal) {

        // m = lbs/day, multiplies by 7 to get lbs/week
        double actualWeeklyRate = m * 7;

        // Checks gap between actual weight change and user goal
        // Positive gap = loss/gain too slow
        // Negative gap = loss/gain too fast
        double gap = weeklyRateGoal - Math.abs(actualWeeklyRate); //

        // Convert lbs/week gap to cal/day adjustment (3500/7)
        double calorieAdjustment = gap * 500;
        double adjustedCalories = currentCalories - calorieAdjustment;

        // Min calories
        if (adjustedCalories < 1200) {
            adjustedCalories = 1200;
        }

        return adjustedCalories;
    }

    // Getter for graphs
    public double getSlope() {
        return m;
    }

}
