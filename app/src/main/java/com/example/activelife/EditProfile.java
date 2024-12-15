package com.example.activelife;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProfile extends AppCompatActivity {

    private EditText  gender, age, current_weight,height, username;
    private TextView fullName,namee,editusername;
    private Button saveButton;
    private ImageButton back;
    private ImageView profile_picture;
    private UserDatabaseHelper dbHelper;
    private long userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        userId = sharedPreferences.getLong("userId", -1);

        // Initialize database helper
        dbHelper = new UserDatabaseHelper(this);

        fullName = findViewById(R.id.full_name);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        current_weight = findViewById(R.id.current_weight);
        height = findViewById(R.id.height);
        username = findViewById(R.id.editusername);
        saveButton = findViewById(R.id.save_button);
        back = findViewById(R.id.back_button);
        namee = findViewById(R.id.name);
        editusername = findViewById(R.id.username);
        profile_picture = findViewById(R.id.profile_picture);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditProfile.this, Profile.class);
                startActivity(i);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Collect input data
                String userGender = gender.getText().toString();
                String userAge = age.getText().toString();
                String userCurrentWeight = current_weight.getText().toString();
                String userHeight = height.getText().toString();
                String userUsername = username.getText().toString();

                // Logic for updating profile information in the database
                updateUserProfile(userId, userGender, userAge,userCurrentWeight,userHeight);
            }
        });

        // Load any existing data if required
        loadUserProfile();
    }

    private void loadUserProfile() {
        // Load existing user profile data from the database
        Cursor cursor = dbHelper.getUserInfo(userId);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_NAME));
            String userGender = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_GENDER));
            String userAge = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_AGE));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_EMAIL));
            String userCurrentWeight=cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_CURRENT_WEIGHT));
            String userHeight=cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_HEIGHT));


            namee.setText(name);
            fullName.setText(name);
            gender.setText(userGender);
            age.setText(userAge);
            current_weight.setText(userCurrentWeight);
            height.setText(userHeight);

            username.setText("@"+name);
            editusername.setText(userEmail);

            if ("Female".equalsIgnoreCase(userGender)) {
                profile_picture.setImageResource(R.drawable.femaleprofile); // Replace with your female image resource
            }
        }
        if (cursor != null) {
            cursor.close();
        }

    }

    private void updateUserProfile(long userId,String gender, String age, String userCurrentWeight,String userHeight) {
        // Prepare the data to update
        ContentValues values = new ContentValues();
        values.put(UserDatabaseHelper.COLUMN_USER_GENDER, gender);
        values.put(UserDatabaseHelper.COLUMN_USER_AGE, age);
        values.put(UserDatabaseHelper.COLUMN_USER_CURRENT_WEIGHT,userCurrentWeight);
        values.put(UserDatabaseHelper.COLUMN_USER_HEIGHT,userHeight);




        // Update the user info in the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.update(UserDatabaseHelper.TABLE_USER_INFO, values, UserDatabaseHelper.COLUMN_FOREIGN_KEY_USER_ID + " = ?", new String[]{String.valueOf(userId)});


        // Provide feedback to the user
        if (rowsAffected > 0) {
            Toast.makeText(EditProfile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EditProfile.this, "Error Updating Profile", Toast.LENGTH_SHORT).show();
        }
    }



}