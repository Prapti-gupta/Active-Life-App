package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
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
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                overridePendingTransition(0, 0);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if at least one gender is selected
                if (!isSelected[0] && !isSelected[1]) {
                    Toast.makeText(GetSex.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                } else {
                    // Determine which gender is selected and pass it
                    String gender = isSelected[0] ? "Male" : "Female"; // Assuming the first button is male

                    SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Gender",gender);
                    editor.apply();

                    Intent i = new Intent(GetSex.this, GetAge.class);
                    startActivity(i);

                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
}