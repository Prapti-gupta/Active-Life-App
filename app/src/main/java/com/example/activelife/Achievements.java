package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Achievements extends AppCompatActivity {

    private UserDatabaseHelper dbHelper;
    int workoutCount;
    ImageView b1, b2, b3, b4, b5, b6;
    TextView t1,t2,t3,t4,t5,t6;

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

        t1= findViewById(R.id.bt1);
        t2= findViewById(R.id.bt2);
        t3= findViewById(R.id.bt3);
        t4= findViewById(R.id.bt4);
        t5= findViewById(R.id.bt5);
        t6= findViewById(R.id.bt6);




        ImageButton back=(ImageButton) findViewById(R.id.bb1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Achievements.this, Profile.class);
                startActivity(i);
            }
        });

        workoutCount=getCompletedWorkoutsCount();

        if (workoutCount >= 1) {
            b1.setImageResource(R.drawable.badge1);
            t1.setText("Rising Star \uD83C\uDF1F");
        }

        if (workoutCount >= 2) {
            b2.setImageResource(R.drawable.badge2);
            t2.setText("Consistency \uD83C\uDFC5");
        }

        if (workoutCount >= 3) {
            b3.setImageResource(R.drawable.badge3);
            t3.setText("Sweat Warrior \uD83C\uDFCB\uFE0F\u200D♂\uFE0F");
        }

        if (workoutCount >= 4) {
            b4.setImageResource(R.drawable.badge4);
            t4.setText("Peak Performer ⛰\uFE0F");
        }

        if (workoutCount >= 5) {
            b5.setImageResource(R.drawable.badge5);
            t5.setText("Fit Legend \uD83C\uDFC6");
        }

        if (workoutCount >= 6) {
            b6.setImageResource(R.drawable.badge6);
            t6.setText("Health Hero \uD83E\uDDB8\u200D");
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