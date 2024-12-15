package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GetHeight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_height);

        //values from the previous activity
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String gender= getIntent().getStringExtra("gender");
        String age=getIntent().getStringExtra("age");
        String activity_level=getIntent().getStringExtra("activity_level");


        Button next = findViewById(R.id.next);
        Button back = findViewById(R.id.back);

        NumberPicker numberPicker = findViewById(R.id.age_picker);

        //to set the range of the NumberPicker
        numberPicker.setMinValue(91);
        numberPicker.setMaxValue(271);

        //to set the displayed values
        numberPicker.setDisplayedValues(generateHeightRange());

        //to set the default value
        numberPicker.setValue(160);

        //applies custom layout for items
        numberPicker.setFormatter(value -> String.valueOf(value));
        numberPicker.setWrapSelectorWheel(true);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetHeight.this, GetActive.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetHeight.this, GetWeight.class);

                // Pass the previous values along with the selected height
                i.putExtra("email",email);
                i.putExtra("password",password);
                i.putExtra("name",name);
                i.putExtra("city", city);
                i.putExtra("gender", gender);
                i.putExtra("age", age);
                i.putExtra("activity_level", activity_level);
                i.putExtra("height", String.valueOf(numberPicker.getValue()));

                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private String[] generateHeightRange() {
        int minHeight = 91;
        int maxHeight = 271;
        String[] values = new String[maxHeight - minHeight + 1];
        for (int i = 0; i < values.length; i++) {
            values[i] = String.valueOf(minHeight + i);
        }
        return values;
    }
}