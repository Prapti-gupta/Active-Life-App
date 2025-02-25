package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BmiCalculator extends AppCompatActivity {

    TextView currentHeight, currentAge, currentWeight;
    ImageView incrementAge, incrementWeight, decrementWeight, decrementAge;
    SeekBar seekBarForHeight;
    RelativeLayout male, female;

    int intWeight = 55;
    int intAge = 22;
    int currentProgress;
    String intProgress = "170"; // Height
    String typeOfUser = "0"; // Gender: 0 = Not selected, "Male" or "Female"
    String weight2 = "55";
    String age2 = "22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        // Initialize UI elements
        Button calculateBmi = findViewById(R.id.calculatebmi);
        currentAge = findViewById(R.id.currentage);
        currentWeight = findViewById(R.id.currentweight);
        currentHeight = findViewById(R.id.currentheight);
        incrementAge = findViewById(R.id.increamentage);
        decrementAge = findViewById(R.id.decreamentage);
        incrementWeight = findViewById(R.id.increamentweight);
        decrementWeight = findViewById(R.id.decreamentweight);
        seekBarForHeight = findViewById(R.id.seekbarForHeight);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        ImageButton back = findViewById(R.id.back_button);

        // Back button action
        back.setOnClickListener(view -> {
            Intent i = new Intent(BmiCalculator.this, Profile.class);
            startActivity(i);
        });

        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        // Retrieve data from database
        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
        Cursor cursor = dbHelper.getUserInfo(userId);

        if (cursor != null && cursor.moveToFirst()) {
            // Extract data from cursor
            int ageValue = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_AGE));
            int heightValue = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_HEIGHT));
            int weightValue = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_CURRENT_WEIGHT));
            String genderValue = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_GENDER));

            // Set default values from the database
            intAge = ageValue;
            intWeight = weightValue;
            intProgress = String.valueOf(heightValue);
            typeOfUser = genderValue;

            currentAge.setText(String.valueOf(intAge));
            currentWeight.setText(String.valueOf(intWeight));
            currentHeight.setText(intProgress);

            if (typeOfUser.equalsIgnoreCase("Male")) {
                male.setBackground(ContextCompat.getDrawable(this, R.drawable.blue_border_frame));
            } else if (typeOfUser.equalsIgnoreCase("Female")) {
                female.setBackground(ContextCompat.getDrawable(this, R.drawable.pink_border_frame));
            }

            cursor.close();
        }

        // SeekBar for height
        seekBarForHeight.setMax(300);
        seekBarForHeight.setProgress(Integer.parseInt(intProgress));
        seekBarForHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
                intProgress = String.valueOf(currentProgress);
                currentHeight.setText(intProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Increment and decrement buttons for age
        incrementAge.setOnClickListener(view -> {
            intAge++;
            age2 = String.valueOf(intAge);
            currentAge.setText(age2);
        });

        decrementAge.setOnClickListener(view -> {
            intAge--;
            age2 = String.valueOf(intAge);
            currentAge.setText(age2);
        });

        // Increment and decrement buttons for weight
        incrementWeight.setOnClickListener(view -> {
            intWeight++;
            weight2 = String.valueOf(intWeight);
            currentWeight.setText(weight2);
        });

        decrementWeight.setOnClickListener(view -> {
            intWeight--;
            weight2 = String.valueOf(intWeight);
            currentWeight.setText(weight2);
        });

        // Gender selection
        male.setOnClickListener(view -> {
            male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.blue_border_frame));
            female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_edit_text));
            typeOfUser = "Male";
        });

        female.setOnClickListener(view -> {
            female.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pink_border_frame));
            male.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.border_edit_text));
            typeOfUser = "Female";
        });

        // Calculate BMI button action
        calculateBmi.setOnClickListener(view -> {
            if (typeOfUser.equals("0")) {
                Toast.makeText(BmiCalculator.this, "Select Your Gender First", Toast.LENGTH_SHORT).show();
            } else if (intProgress.equals("0")) {
                Toast.makeText(BmiCalculator.this, "Select Your Height First", Toast.LENGTH_SHORT).show();
            } else if (intAge <= 0) {
                Toast.makeText(BmiCalculator.this, "Age is Invalid", Toast.LENGTH_SHORT).show();
            } else if (intWeight <= 0) {
                Toast.makeText(BmiCalculator.this, "Weight is Invalid", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(BmiCalculator.this, BmiResult.class);
                i.putExtra("gender", typeOfUser);
                i.putExtra("height", intProgress);
                i.putExtra("weight", weight2);
                i.putExtra("age", age2);
                startActivity(i);
            }
        });
    }
}
