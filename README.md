# Calorie Recommendation & Nutrition Tracking Application (C964)

This project was developed for WGU's Computer Science Capstone. The goal of the project was to create an application that uses machine learning to solve a real world problem.

The project is a desktop application for a nutrition tracking tool for personal trainers. Trainers can create client profiles, log daily weigh-ins, and receive calorie recommendations that adjust based on client weight trends.

The UI was developed using JavaFX.

## Project Requirements

Below are some of the project requirements and how I implemented them:
- Input validation for user entered data
  - Required fields, numeric data types, and logical consistency checks
- Persistent data storage
  - SQLite
- A machine learning component
  - Linear regression
- Three data visualizations
  - Weight trend line graph
  - Weight logging streak calendar
  - Goal progress visualization and predicted completion date

## Calorie Recommendation

The application uses the Mifflin-St Jeor equation to calculate an initial calorie recommendation based on the following client information:
- Weight
- Height
- Age
- Sex
- Activity level
- Weight goal
- Target rate of loss/gain

The calculated recommendation is adjusted based on whether the client is trying to lose, gain, or maintain weight.

## Linear Regression

Linear regression is used to analyze the client's logged weight history and determine their weight trend over time.

The client's actual rate of weight change is compared to their target rate. Based on the difference, the application adjusts the calorie recommendation to account for whether the client is progressing faster or slower than expected.

The model is also used to predict future weight and estimate when the client will reach their goal.

## Database

Client profiles and weight logs are stored locally using SQLite.

The database contains separate tables for client profiles and weight logs. Weight entries are associated with the appropriate client through a foreign key relationship.

Input is validated before being stored, and prepared SQL statements are used for database queries.
