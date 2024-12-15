package com.example.activelife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class WorkoutCompleted extends AppCompatActivity {
    private TextView[] stars;
    private TextView workoutNameView, workoutTimeView, workoutCalorieView, workoutExerciseView;
    private int workoutId,  workoutImg, workoutCalorie, exerciseCount;
    private String workoutTime,workoutName;
    private int selectedRating = 0;
    private UserDatabaseHelper dbHelper; // Declare DatabaseHelper instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_completed);

        // Initialize views
        workoutNameView = findViewById(R.id.workoutName);
        workoutTimeView = findViewById(R.id.time);
        workoutCalorieView = findViewById(R.id.calorie);
        workoutExerciseView = findViewById(R.id.exercise);

        // Get data from Intent with default values
        workoutId = getIntent().getIntExtra("workoutId", 0);
        workoutName = getIntent().getStringExtra("workoutName");
        workoutTime = getIntent().getStringExtra("workoutTime");
        workoutImg = getIntent().getIntExtra("workoutImg", 0);
        workoutCalorie = getIntent().getIntExtra("caloriesBurnt", 0);
        exerciseCount = getIntent().getIntExtra("exerciseCount", 0);

        // Set data to TextViews
        workoutNameView.setText(workoutName != null ? workoutName : "Workout Name Not Available");
        workoutTimeView.setText(String.valueOf(workoutTime));
        workoutCalorieView.setText(String.valueOf(workoutCalorie));
        workoutExerciseView.setText(String.valueOf(exerciseCount));

        // Initialize stars for rating
        stars = new TextView[]{
                findViewById(R.id.star1),
                findViewById(R.id.star2),
                findViewById(R.id.star3),
                findViewById(R.id.star4),
                findViewById(R.id.star5)
        };

        // Set onClickListener for each star to select rating
        for (int i = 0; i < stars.length; i++) {
            final int rating = i + 1; // Rating value from 1 to 5
            stars[i].setOnClickListener(v -> selectRating(rating));
        }

        // Initialize DatabaseHelper
        dbHelper = new UserDatabaseHelper(this);

        // Set up submit button
        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(v -> {
            if (selectedRating == 0) {
                Toast.makeText(WorkoutCompleted.this, "Please select a rating.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save workout details to database
            saveWorkoutDetails(selectedRating);
        });
    }

    private void selectRating(int rating) {
        selectedRating = rating; // Update selected rating
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setTextColor(ContextCompat.getColor(this, R.color.yellow)); // Change star color to yellow
            } else {
                stars[i].setTextColor(ContextCompat.getColor(this, R.color.darkgrey)); // Change star color to dark grey
            }
        }
    }

    private void saveWorkoutDetails(int rating) {

        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);
        String userIdString = String.valueOf(userId);

        // Insert completed workout into the database
        dbHelper.insertCompletedWorkout(userIdString,workoutId, workoutName, workoutTime, workoutCalorie, rating);

        // Show success message
        Toast.makeText(this, "Workout details saved successfully.", Toast.LENGTH_SHORT).show();

        // Navigate back to home or another activity
        Intent intent = new Intent(WorkoutCompleted.this, HomePage.class);

        startActivity(intent);
        finish();
    }
}
