package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class GetWeight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_weight);

        //values from the previous activity
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String gender= getIntent().getStringExtra("gender");
        String age=getIntent().getStringExtra("age");
        String activity_level=getIntent().getStringExtra("activity_level");
        String height=getIntent().getStringExtra("height");

        NumberPicker kgPicker = findViewById(R.id.kg_picker);
        NumberPicker gPicker = findViewById(R.id.g_picker);

        // Set range for kgPicker (0 to 300)
        kgPicker.setMinValue(0);
        kgPicker.setMaxValue(300);

        // Set range for gPicker (0 to 9)
        gPicker.setMinValue(0);
        gPicker.setMaxValue(9);

        // Set default values
        kgPicker.setValue(60); // Default kg value
        gPicker.setValue(0);   // Default gram value

        // Apply custom layout for items
        kgPicker.setFormatter(value -> String.valueOf(value));
        gPicker.setFormatter(value -> String.valueOf(value));

        // Wrap the selector wheel
        kgPicker.setWrapSelectorWheel(true);
        gPicker.setWrapSelectorWheel(true);


        Button next = findViewById(R.id.next);
        Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetWeight.this, GetHeight.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetWeight.this, GetTargetWeight.class);

                i.putExtra("email",email);
                i.putExtra("password",password);
                i.putExtra("name",name);
                i.putExtra("city", city);
                i.putExtra("gender", gender);
                i.putExtra("age", age);
                i.putExtra("activity_level", activity_level);
                i.putExtra("height", height);
                // Pass the selected weight as a combined value in grams
                int kgValue = kgPicker.getValue();
                int gValue = gPicker.getValue();
                int totalWeightInGrams = (kgValue * 1000 + gValue)/1000; // Convert kg to grams and add grams
                String total=String.valueOf(totalWeightInGrams);

                i.putExtra("weight", total); // Pass the total weight in grams

                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private String[] generateWeightRange() {
        int minWeight = 30;
        int maxWeight = 300;
        String[] values = new String[maxWeight - minWeight + 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = String.valueOf(minWeight + i);
        }
        return values;
    }
}