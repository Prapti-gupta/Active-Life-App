package com.example.activelife;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Achievements extends AppCompatActivity {

    private UserDatabaseHelper dbHelper;
    int workoutCount;
    ImageView b1, b2, b3, b4, b5, b6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        dbHelper = new UserDatabaseHelper(this);

        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);

        workoutCount=getCompletedWorkoutsCount();

        if (workoutCount >= 1) {
            b1.setImageResource(R.drawable.badge1);
        }

        if (workoutCount >= 2) {
            b2.setImageResource(R.drawable.badge2);
        }

        if (workoutCount >= 3) {
            b3.setImageResource(R.drawable.badge3);
        }

        if (workoutCount >= 4) {
            b4.setImageResource(R.drawable.badge4);
        }

        if (workoutCount >= 5) {
            b5.setImageResource(R.drawable.badge5);
        }

        if (workoutCount >= 6) {
            b6.setImageResource(R.drawable.badge6);
        }

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