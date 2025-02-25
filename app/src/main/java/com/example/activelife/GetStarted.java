package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        String email = sharedPreferences.getString("Email_Id", "");
        String password = sharedPreferences.getString("Password", "");
        String name = sharedPreferences.getString("Name", "");
        String city = sharedPreferences.getString("Location", "");
        String gender = sharedPreferences.getString("Gender", "");
        String age = sharedPreferences.getString("Age", "");
        String activity_level = sharedPreferences.getString("Activity_Level", "");
        String height = sharedPreferences.getString("Height", "");
        String weight = sharedPreferences.getString("Weight", "");
        String targetweight = sharedPreferences.getString("Target_Weight", "");
        String medicalConditions = sharedPreferences.getString("Medical_Conditions", "");

        // Create an instance of UserDatabaseHelper
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(GetStarted.this);

        Button getStartedButton = findViewById(R.id.get_started_button);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long userId = dbHelper.insertUser(name, email, password);
                if (userId == -1) {
                    Toast.makeText(GetStarted.this, "Error creating user!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = dbHelper.insertUserInfo(userId, city, gender, age, activity_level, height, weight, targetweight, medicalConditions);

                if (result != -1) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("userId", userId);
                    editor.putBoolean("isLoggedIn", true);
                    editor.commit();  // Change apply() to commit() for debugging
                    Toast.makeText(GetStarted.this, "User information saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GetStarted.this, "Error saving user information!", Toast.LENGTH_SHORT).show();
                }

                Intent i = new Intent(GetStarted.this, HomePage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}
