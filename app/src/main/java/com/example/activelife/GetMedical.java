package com.example.activelife;

import android.content.Intent;
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

        //values from the previous activity
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        String name = getIntent().getStringExtra("name");
        String city = getIntent().getStringExtra("city");
        String gender= getIntent().getStringExtra("gender");
        String age=getIntent().getStringExtra("age");
        String activity_level=getIntent().getStringExtra("activity_level");
        String height=getIntent().getStringExtra("height");
        String weight=getIntent().getStringExtra("weight");
        String targetweight=getIntent().getStringExtra("targetweight");

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
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

                    // Create Intent for the next activity
                    Intent i = new Intent(GetMedical.this, GetStarted.class);

                    i.putExtra("email",email);
                    i.putExtra("password",password);
                    i.putExtra("name",name);
                    i.putExtra("city", city);
                    i.putExtra("gender", gender);
                    i.putExtra("age", age);
                    i.putExtra("activity_level", activity_level);
                    i.putExtra("height", height);
                    i.putExtra("weight",weight);
                    i.putExtra("targetweight", targetweight);

                    // Pass the medical conditions as an extra
                    i.putExtra("medical_conditions", selectedConditions.toString());
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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

                        // Create Intent for the next activity
                        Intent i = new Intent(GetMedical.this, GetStarted.class);

                        i.putExtra("email",email);
                        i.putExtra("password",password);
                        i.putExtra("name",name);
                        i.putExtra("city", city);
                        i.putExtra("gender", gender);
                        i.putExtra("age", age);
                        i.putExtra("activity_level", activity_level);
                        i.putExtra("height", height);
                        i.putExtra("weight",weight);
                        i.putExtra("targetweight", targetweight);

                        // Pass the medical conditions as an extra
                        i.putExtra("medical_conditions", selectedConditions.toString());
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