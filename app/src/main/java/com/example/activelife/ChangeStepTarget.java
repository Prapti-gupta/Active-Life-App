package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChangeStepTarget extends AppCompatActivity {

    private EditText stepTargetEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_step_target);


        stepTargetEditText = findViewById(R.id.stepTargetEditText);
        saveButton = findViewById(R.id.saveButton);


        ImageButton back=(ImageButton) findViewById(R.id.bb1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangeStepTarget.this, Profile.class);
                startActivity(i);
            }
        });

        // Load current target from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        int currentTarget = sharedPreferences.getInt("stepCountTarget", 5000); // Default target is 5000
        stepTargetEditText.setText(String.valueOf(currentTarget));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newTarget = Integer.parseInt(stepTargetEditText.getText().toString());

                // Save the new target to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("stepCountTarget", newTarget);
                editor.apply();

                Toast.makeText(ChangeStepTarget.this, "Step target updated!", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity and go back to StepCounter
            }
        });
    }
}