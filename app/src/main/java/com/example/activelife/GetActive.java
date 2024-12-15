package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetActive extends AppCompatActivity {
    private ImageButton[] activeButtons = new ImageButton[3];
    private boolean[] isSelected = new boolean[3];
    private String selectedActivityLevel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_active);

        //values from the previous activity
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String gender= getIntent().getStringExtra("gender");
        String age=getIntent().getStringExtra("age");

        activeButtons[0] = findViewById(R.id.little_active);
        activeButtons[1] = findViewById(R.id.moderate_active);
        activeButtons[2] = findViewById(R.id.high_active);

        for (int i = 0; i < activeButtons.length; i++) {
            final int index = i; // Required for the inner class
            activeButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Deselect all buttons
                    for (int j = 0; j < activeButtons.length; j++) {
                        if (j != index && isSelected[j]) {
                            activeButtons[j].setBackgroundResource(R.drawable.border_edit_text);
                            isSelected[j] = false;
                        }
                    }

                    // Toggle the selected button
                    if (isSelected[index]) {
                        activeButtons[index].setBackgroundResource(R.drawable.border_edit_text);
                        isSelected[index] = false;
                    } else {
                        activeButtons[index].setBackgroundResource(R.drawable.border_button_selected);
                        isSelected[index] = true;

                        // Set the selected activity level based on the button clicked
                        switch (index) {
                            case 0:
                                selectedActivityLevel = "Little Active";
                                break;
                            case 1:
                                selectedActivityLevel = "Moderate Active";
                                break;
                            case 2:
                                selectedActivityLevel = "High Active";
                                break;
                        }
                    }
                }
            });
        }

        Button next = findViewById(R.id.next);
        Button back = findViewById(R.id.back);

        // Back button functionality
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetActive.this, GetAge.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Next button functionality
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if at least one activity level is selected
                if (!isSelected[0] && !isSelected[1] && !isSelected[2]) {
                    Toast.makeText(GetActive.this, "Please select your activity level", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(GetActive.this, GetHeight.class);
                    i.putExtra("email",email);
                    i.putExtra("password",password);
                    i.putExtra("name",name);
                    i.putExtra("city", city);
                    i.putExtra("gender", gender);
                    i.putExtra("age", age);
                    i.putExtra("activity_level", selectedActivityLevel);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }
}