package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GetMedical extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_medical);

        Button next = findViewById(R.id.next);
        Button back = findViewById(R.id.back);

        RadioGroup medicalConditionGroup = findViewById(R.id.medicalCondition);
        LinearLayout checkboxLayout1 = findViewById(R.id.layout1);
        LinearLayout checkboxLayout2 = findViewById(R.id.layout2);
        LinearLayout checkboxLayout3 = findViewById(R.id.layout3);
        LinearLayout checkboxLayout4 = findViewById(R.id.layout4);
        LinearLayout checkboxLayout5 = findViewById(R.id.layout5);
        LinearLayout checkboxLayout6 = findViewById(R.id.layout6);
        View mainline = findViewById(R.id.mainline);

        medicalConditionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.yesRadioButton) {
                    // Shows the checkbox layout when "Yes" is selected
                    checkboxLayout1.setVisibility(View.VISIBLE);
                    checkboxLayout2.setVisibility(View.VISIBLE);
                    checkboxLayout3.setVisibility(View.VISIBLE);
                    checkboxLayout4.setVisibility(View.VISIBLE);
                    checkboxLayout5.setVisibility(View.VISIBLE);
                    checkboxLayout6.setVisibility(View.VISIBLE);
                    mainline.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.noRadioButton) {
                    // Hides the checkbox layout when "No" is selected
                    checkboxLayout1.setVisibility(View.GONE);
                    checkboxLayout2.setVisibility(View.GONE);
                    checkboxLayout3.setVisibility(View.GONE);
                    checkboxLayout4.setVisibility(View.GONE);
                    checkboxLayout5.setVisibility(View.GONE);
                    checkboxLayout6.setVisibility(View.GONE);
                    mainline.setVisibility(View.GONE);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetMedical.this, GetTargetWeight.class);
                startActivity(i);
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                overridePendingTransition(0, 0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder selectedConditions = new StringBuilder(); // To store selected conditions
                // Check if a radio button is selected
                if (medicalConditionGroup.getCheckedRadioButtonId() == -1) {
                    // No radio button is selected
                    Toast.makeText(GetMedical.this, "Please select an option", Toast.LENGTH_SHORT).show();
                } else if (medicalConditionGroup.getCheckedRadioButtonId() == R.id.noRadioButton) {
                    selectedConditions.append("None");

                    SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Medical_Condition",selectedConditions.toString());
                    editor.apply();

                    // Create Intent for the next activity
                    Intent i = new Intent(GetMedical.this, GetStarted.class);
                    startActivity(i);

                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    overridePendingTransition(0, 0);

                } else if (medicalConditionGroup.getCheckedRadioButtonId() == R.id.yesRadioButton) {
                    // Check if at least one checkbox is selected
                    boolean isAnyCheckboxSelected = false;


                    // Check each checkbox layout and append to the selectedConditions
                    if (((CheckBox) checkboxLayout1.findViewById(R.id.pcos)).isChecked()) {
                        selectedConditions.append("PCOS, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout1.findViewById(R.id.pcod)).isChecked()) {
                        selectedConditions.append("PCOD, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout2.findViewById(R.id.thyroid)).isChecked()) {
                        selectedConditions.append("Thyroid, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout2.findViewById(R.id.pregnancy)).isChecked()) {
                        selectedConditions.append("Pregnancy, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout3.findViewById(R.id.diabetes)).isChecked()) {
                        selectedConditions.append("Diabetes, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout3.findViewById(R.id.hypertension)).isChecked()) {
                        selectedConditions.append("Hypertension, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout4.findViewById(R.id.physicalInjury)).isChecked()) {
                        selectedConditions.append("Physical Injury, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout4.findViewById(R.id.breathing)).isChecked()) {
                        selectedConditions.append("Breathing Issues, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout5.findViewById(R.id.arthritis)).isChecked()) {
                        selectedConditions.append("Arthritis, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout5.findViewById(R.id.preDiabetes)).isChecked()) {
                        selectedConditions.append("Pre-Diabetes, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout6.findViewById(R.id.cholesterol)).isChecked()) {
                        selectedConditions.append("Cholesterol, ");
                        isAnyCheckboxSelected = true;
                    }
                    if (((CheckBox) checkboxLayout6.findViewById(R.id.other)).isChecked()) {
                        selectedConditions.append("Other, ");
                        isAnyCheckboxSelected = true;
                    }

                    if (!isAnyCheckboxSelected) {
                        Toast.makeText(GetMedical.this, "Please select at least one checkbox", Toast.LENGTH_SHORT).show();
                    } else {
                        // Remove the last comma and space if any conditions were selected
                        if (selectedConditions.length() > 0) {
                            selectedConditions.setLength(selectedConditions.length() - 2);
                        }

                        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("Medical_Conditions",selectedConditions.toString());
                        editor.apply();

                        // Create Intent for the next activity
                        Intent i = new Intent(GetMedical.this, GetStarted.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } else {

                    // If no medical conditions are applicable
                    Intent i = new Intent(GetMedical.this, GetStarted.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

    }
}