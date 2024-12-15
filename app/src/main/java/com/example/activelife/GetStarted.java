package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GetStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //values from the previous activity
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String gender= getIntent().getStringExtra("gender");
        String age=getIntent().getStringExtra("age");
        String activity_level=getIntent().getStringExtra("activity_level");
        String height=getIntent().getStringExtra("height");
        String weight=getIntent().getStringExtra("weight");
        String targetweight=getIntent().getStringExtra("targetweight");
        String medicalConditions = getIntent().getStringExtra("medical_conditions");

        // Create an instance of UserDatabaseHelper
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(GetStarted.this);


        Button b1 = (Button) findViewById(R.id.get_started_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long userId = dbHelper.insertUser(name, email, password);

                long result = dbHelper.insertUserInfo(userId, city, gender, age, activity_level, height, weight, targetweight, medicalConditions);

                if (result != -1) {
                    SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putLong("userId", userId);
                    editor.putBoolean("isLoggedIn", true); // Save login status
                    editor.apply();
                    Toast.makeText(GetStarted.this, "User information saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(GetStarted.this, "Error saving user information!", Toast.LENGTH_SHORT).show();
                }

                Intent i= new Intent(GetStarted.this, HomePage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });

    }
}