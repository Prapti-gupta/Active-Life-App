package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
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
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                overridePendingTransition(0, 0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Height",String.valueOf(numberPicker.getValue()));
                editor.apply();


                Intent i = new Intent(GetHeight.this, GetWeight.class);
                startActivity(i);

                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                overridePendingTransition(0, 0);
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