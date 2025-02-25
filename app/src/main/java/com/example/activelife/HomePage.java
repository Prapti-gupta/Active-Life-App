package com.example.activelife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomePage extends AppCompatActivity {
    private UserDatabaseHelper dbHelper;
    private ArrayList<Workout> workoutList;
    private WorkoutAdapter workoutAdapter;
    public TextView streaktext;
    public TextView workouttext;
    public ImageView stepsImage;
    public TextView stepText;
    public TextView stepText2;
    private long userId;
    private List<String> quotesList;
    private TextView quoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ImageButton home = findViewById(R.id.homeButton);
        ImageButton meal = findViewById(R.id.mealButton);
        ImageButton workout = findViewById(R.id.workoutButton);
        ImageButton profile = findViewById(R.id.profileButton);

        stepsImage = findViewById(R.id.steps);
        stepText = findViewById(R.id.steptext);
        stepText2 = findViewById(R.id.steptext2);
        streaktext = findViewById(R.id.streaktext);
        workouttext = findViewById(R.id.workouttext);

        ImageView profile_picture=(ImageView)findViewById(R.id.imageView);
        TextView username=(TextView)findViewById(R.id.textView5);

        quoteTextView = findViewById(R.id.quoteTextView1);

        // Initialize the SQLite database helper and load quotes
        dbHelper = new UserDatabaseHelper(this);
        quotesList = dbHelper.getAllQuotes();

        // Display a random quote from the SQLite database
        if (quotesList != null && !quotesList.isEmpty()) {
            displayRandomQuote();
        } else {
            quoteTextView.setText("Stay motivated!");
        }

        // Call updateStreak to display and update the current streak count
        updateStreak();

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
            Cursor cursor = dbHelper.getUserInfo(userId);

            if (cursor != null && cursor.moveToFirst()) {

                String name = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_NAME));
                String genderValue = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_GENDER));
                username.setText(name);

                if ("Female".equalsIgnoreCase(genderValue)) {
                    profile_picture.setImageResource(R.drawable.femaleprofile); // Replace with your female image resource
                }

                cursor.close();
            }
        }

        // Set up click listeners for navigation
        stepsImage.setOnClickListener(view -> startActivity(new Intent(HomePage.this, StepCounter.class)));
        stepText.setOnClickListener(view -> startActivity(new Intent(HomePage.this, StepCounter.class)));
        stepText2.setOnClickListener(view -> startActivity(new Intent(HomePage.this, StepCounter.class)));

        home.setOnClickListener(view -> startActivity(new Intent(HomePage.this, HomePage.class)));
        meal.setOnClickListener(view -> startActivity(new Intent(HomePage.this, MealPlanning.class)));
        workout.setOnClickListener(view -> startActivity(new Intent(HomePage.this, WorkoutPlanning.class)));
        profile.setOnClickListener(view -> startActivity(new Intent(HomePage.this, Profile.class)));

        dbHelper = new UserDatabaseHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        workoutList = new ArrayList<>();
        loadWorkouts();

        workoutAdapter = new WorkoutAdapter(this, workoutList, selectedWorkout -> {
            Intent intent = new Intent(HomePage.this, ExerciseActivity.class);
            intent.putExtra("workoutId", selectedWorkout.getId());
            startActivity(intent);
        }, R.layout.workout_item);

        recyclerView.setAdapter(workoutAdapter);

        // Load completed workouts data
        updateCompletedWorkoutsInfo();
    }


    private void displayRandomQuote() {
        Random random = new Random();
        int randomIndex = random.nextInt(quotesList.size());
        quoteTextView.setText("\"" +quotesList.get(randomIndex)+ "\"");
    }



    // Method to update daily streak
    private void updateStreak() {
        // Access SharedPreferences to retrieve and store streak data
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Set up date formatting to compare dates in "yyyy-MM-dd" format (e.g., "2024-11-03")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String today = sdf.format(new Date()); // Get today's date in the specified format
        String lastCompletedDate = sharedPreferences.getString("lastCompletedDate", ""); // Retrieve the last recorded completion date

        // Get the current streak count, or set to 0 if not previously set
        int currentStreak = sharedPreferences.getInt("currentStreak", 0);

        // Check if today’s date is already recorded to avoid multiple updates in a day
        if (!lastCompletedDate.equals(today)) {

            // Set up a Calendar instance to calculate the date of 'yesterday'
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -1); // Move back one day to get 'yesterday's date
            String yesterday = sdf.format(calendar.getTime());

            // Check if the last completion date was yesterday
            if (lastCompletedDate.equals(yesterday)) {
                // If yes, increase the streak count as the user has logged in consecutively
                currentStreak++;
            } else {
                // If not yesterday, reset the streak to 1, starting a new streak
                currentStreak = 1;
            }

            // Save the updated date and streak count in SharedPreferences
            editor.putString("lastCompletedDate", today); // Save today’s date as the last completed date
            editor.putInt("currentStreak", currentStreak); // Save the updated streak count
            editor.apply(); // Commit the changes
        }

        // Display the current streak to the user by setting it on a TextView
        streaktext.setText(" " + currentStreak); // Update the TextView to show the current streak count
    }


    private void loadWorkouts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(UserDatabaseHelper.TABLE_WORKOUT, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_ID);
                    int nameIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_NAME);
                    int imageIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_IMAGE);
                    int timeIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_TIME);

                    if (idIndex != -1 && nameIndex != -1 && imageIndex != -1 && timeIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        int imageResId = cursor.getInt(imageIndex);
                        String time = cursor.getString(timeIndex);

                        workoutList.add(new Workout(id, name, imageResId, time));
                    } else {
                        Log.e("DatabaseError", "Column not found in the cursor");
                    }
                } while (cursor.moveToNext());
            } else {
                Log.e("DatabaseError", "Cursor is empty or null");
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error loading workouts: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close(); // Ensure the database is closed in the finally block
        }
    }

    private void updateCompletedWorkoutsInfo() {
        int completedCount = getCompletedWorkoutsCount();
        workouttext.setText(String.valueOf(completedCount)); // Set completed workouts count
    }

    private int getCompletedWorkoutsCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        try {
            // Retrieve the user ID from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
            long userId = sharedPreferences.getLong("userId", -1);

            // Check if userId is valid
            if (userId != -1) {
                // Add WHERE clause to filter by user_id
                cursor = db.rawQuery("SELECT COUNT(*) FROM " + UserDatabaseHelper.TABLE_WORKOUTS_COMPLETED +
                                " WHERE " + UserDatabaseHelper.COLUMN_FOREIGN_KEY_USER_ID1 + " = ?",
                        new String[]{String.valueOf(userId)});

                if (cursor != null && cursor.moveToFirst()) {
                    count = cursor.getInt(0);
                }
            } else {
                Log.e("DatabaseError", "User ID not found in SharedPreferences.");
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error counting completed workouts: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close(); // Ensure the database is closed in the finally block
        }

        return count;
    }

}
