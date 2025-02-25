package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetLocation extends AppCompatActivity {

    private ImageButton[] buttons = new ImageButton[12];
    private boolean[] isSelected = new boolean[12];
    private EditText searchCity; // For displaying selected city name
    private String[] cityNames = {"Mumbai", "Bengaluru", "Delhi", "Kolkata", "Chennai", "Hyderabad", "Ahmedabad", "Pune", "Lucknow", "Jaipur", "Kochi", "Chandigarh"};
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);


        // Initialize EditText
        searchCity = findViewById(R.id.search_city);

        // Initialize ImageButtons
        buttons[0] = findViewById(R.id.mumbai);
        buttons[1] = findViewById(R.id.bengaluru);
        buttons[2] = findViewById(R.id.delhi);
        buttons[3] = findViewById(R.id.kolkata);
        buttons[4] = findViewById(R.id.chennai);
        buttons[5] = findViewById(R.id.hyderabad);
        buttons[6] = findViewById(R.id.ahmedabad);
        buttons[7] = findViewById(R.id.pune);
        buttons[8] = findViewById(R.id.lucknow);
        buttons[9] = findViewById(R.id.jaipur);
        buttons[10] = findViewById(R.id.kochi);
        buttons[11] = findViewById(R.id.chandigarh);

        // Set onClickListeners using a loop
        for (int i = 0; i < buttons.length; i++) {
            final int index = i; // Required for the inner class
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Deselect previously selected button
                    for (int j = 0; j < buttons.length; j++) {
                        if (j != index && isSelected[j]) {
                            buttons[j].setBackgroundResource(R.drawable.border_edit_text); // Reset to original border
                            isSelected[j] = false; // Update the selection state
                        }
                    }

                    // Toggle the selected button and display the city name
                    if (isSelected[index]) {
                        buttons[index].setBackgroundResource(R.drawable.border_edit_text);
                        searchCity.setText(""); // Clear EditText if deselected
                        isSelected[index] = false;
                    } else {
                        buttons[index].setBackgroundResource(R.drawable.border_button_selected);
                        searchCity.setText(cityNames[index]); // Set EditText to selected city name
                        location=cityNames[index]; // Setting variable to pass it to the next page
                        isSelected[index] = true;
                    }
                }
            });
        }

        // Back button functionality
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetLocation.this, GetName.class);
                startActivity(i);
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                overridePendingTransition(0, 0);
            }
        });

        // Next button functionality
        Button next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the value of Location on button click
                String Location = searchCity.getText().toString().trim(); // Trim the input to remove leading/trailing spaces

                // Perform validation
                if (Location.isEmpty()) {
                    searchCity.requestFocus();
                    searchCity.setError("Enter your Location");
                } else if (!Location.matches("[a-zA-Z ]+")) { // Allow spaces between alphabetic characters
                    searchCity.requestFocus();
                    searchCity.setError("Enter only Alphabets and Spaces");
                } else {

                    SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Location",location);
                    editor.apply();

                    Intent i = new Intent(GetLocation.this, GetSex.class);
                    startActivity(i);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    overridePendingTransition(0, 0);
                }
            }
        });
    }
    // Method to filter cities based on search input



}