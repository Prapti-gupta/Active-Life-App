package com.example.activelife;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    private RecyclerView exerciseRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    private ArrayList<String> exerciseList;
    private UserDatabaseHelper dbHelper;
    private int workoutId;
    private String workoutTime;
    private String workoutName;
    private int workoutImg;
    private TextView textViewWorkoutName;
    private ImageView imageViewWorkout;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        // Get workoutId and other details from the intent
        workoutId = getIntent().getIntExtra("workoutId", 1); // Default to 1 if not found

        // Initialize UI components
        textViewWorkoutName = findViewById(R.id.workout_name_text_view);
        imageViewWorkout = findViewById(R.id.workout_image_view);
        startButton = findViewById(R.id.start_button);

        // Initialize Database Helper and exercise list
        dbHelper = new UserDatabaseHelper(this);
        exerciseList = new ArrayList<>();

        // Load workout details and exercises from the database
        loadWorkoutDetails(workoutId);
        loadExercises(workoutId);

        // Set up RecyclerView
        exerciseRecyclerView = findViewById(R.id.recyclerViewExercises);
        exerciseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exerciseAdapter = new ExerciseAdapter(exerciseList, this);
        exerciseRecyclerView.setAdapter(exerciseAdapter);

        // Padding and Divider setup
        int spacingInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        exerciseRecyclerView.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(exerciseRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider)); // Use your drawable
        exerciseRecyclerView.addItemDecoration(dividerItemDecoration);

        // Start button click listener
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Workout object before passing it
                Workout workout = new Workout(workoutId, workoutName, workoutImg, workoutTime); // Use the instance variable workoutTime

                // Pass the exercise list and workout object to WorkoutPlaybackVideoActivity
                Intent intent = new Intent(ExerciseActivity.this, WorkoutPlaybackVideo.class);
                intent.putStringArrayListExtra("exerciseList", exerciseList); // Pass the exercise list
                intent.putExtra("workoutName", workout.getName()); // Pass the workout name from the Workout object
                intent.putExtra("workoutTime", workout.getTime()); // Pass the workout time from the Workout object
                intent.putExtra("workoutImg", workout.getImageResId()); // Pass the workout image ID from the Workout object
                startActivity(intent);
            }
        });
    }

        private void loadWorkoutDetails(int workoutId) {
        Cursor cursor = dbHelper.getWorkoutById(workoutId);

        if (cursor != null && cursor.moveToFirst()) {
            workoutName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_WORKOUT_NAME));
            workoutImg = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_WORKOUT_IMAGE));
            workoutTime = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_WORKOUT_TIME)); // Assuming you have this column

            textViewWorkoutName.setText(workoutName);
            imageViewWorkout.setImageResource(workoutImg);

            Log.d("DB_FETCH", "Workout retrieved: " + workoutName);
        } else {
            Log.d("DB_FETCH", "No workout found for ID: " + workoutId);
        }

        if (cursor != null) {
            cursor.close(); // Ensure cursor is closed after use
        }
    }

    private void loadExercises(int workoutId) {
        Cursor cursor = dbHelper.getExercisesByWorkoutId(workoutId);

        if (cursor != null) {
            Log.d("DB_FETCH", "Cursor count: " + cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    String exerciseName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_EXERCISE_NAME));
                    exerciseList.add(exerciseName);
                    Log.d("DB_FETCH", "Exercise retrieved: " + exerciseName);
                } while (cursor.moveToNext());
            }
            cursor.close(); // Ensure cursor is closed after use
        } else {
            Log.d("DB_FETCH", "Cursor is null, no data found");
        }
    }
}
