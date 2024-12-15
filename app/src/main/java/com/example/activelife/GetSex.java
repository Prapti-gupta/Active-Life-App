package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetSex extends AppCompatActivity {

    private ImageButton[] genderButtons = new ImageButton[2];
    private boolean[] isSelected = new boolean[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_sex);

        Button next = findViewById(R.id.next);
        Button back = findViewById(R.id.back);

        genderButtons[0] = findViewById(R.id.male);
        genderButtons[1] = findViewById(R.id.female);

        // from previous activity
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");

        for (int i = 0; i < genderButtons.length; i++) {
            final int index = i; // Required for the inner class
            genderButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Deselect all buttons
                    for (int j = 0; j < genderButtons.length; j++) {
                        if (j != index && isSelected[j]) {
                            genderButtons[j].setBackgroundResource(R.drawable.border_edit_text);
                            isSelected[j] = false;
                        }
                    }

                    // Toggle the selected button
                    if (isSelected[index]) {
                        genderButtons[index].setBackgroundResource(R.drawable.border_edit_text);
                        isSelected[index] = false;
                    } else {
                        genderButtons[index].setBackgroundResource(R.drawable.border_button_selected);
                        isSelected[index] = true;
                    }
                }
            });
        }

        // Back button functionality
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetSex.this, GetLocation.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // Next button functionality
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if at least one gender is selected
                if (!isSelected[0] && !isSelected[1]) {
                    Toast.makeText(GetSex.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                } else {
                    // Determine which gender is selected and pass it
                    String gender = isSelected[0] ? "Male" : "Female"; // Assuming the first button is male

                    Intent i = new Intent(GetSex.this, GetAge.class);
                    i.putExtra("email",email);
                    i.putExtra("password",password);
                    i.putExtra("name",name);
                    i.putExtra("city", city);
                    i.putExtra("gender", gender);
                    startActivity(i);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }
}