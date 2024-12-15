package com.example.activelife;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BmiCalculator extends AppCompatActivity {

    TextView currentheight,currentage,currentweight;
    ImageView increamentage,increamentweight,decreamentweight,decreamentage;
    SeekBar seekbarforheight;

    RelativeLayout male,female;

    int intweight=55;
    int intage=22;
    int currentprogress;
    String intprogress="170";
    String tyeofuser="0";
    String weight2="55";
    String age2="22";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        Button calulateBmi=(Button)findViewById(R.id.calculatebmi);
        currentage=findViewById(R.id.currentage);
        currentweight=findViewById(R.id.currentweight);
        currentheight=findViewById(R.id.currentheight);
        increamentage=findViewById(R.id.increamentage);
        decreamentage=findViewById(R.id.decreamentage);
        increamentweight=findViewById(R.id.increamentweight);
        decreamentweight=findViewById(R.id.decreamentweight);
        seekbarforheight=findViewById(R.id.seekbarForHeight);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);


        seekbarforheight.setMax(300);
        seekbarforheight.setProgress(170);
        seekbarforheight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentprogress=progress;
                intprogress=String.valueOf(currentprogress);
                currentheight.setText(intprogress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        increamentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intage=intage+1;
                age2=String.valueOf(intage);
                currentage.setText(age2);
            }
        });

        decreamentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intage=intage-1;
                age2=String.valueOf(intage);
                currentage.setText(age2);
            }
        });


        increamentweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight=intweight+1;
                weight2=String.valueOf(intweight);
                currentweight.setText(weight2);
            }
        });

        decreamentweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intweight=intweight-1;
                weight2=String.valueOf(intweight);
                currentweight.setText(weight2);
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                male.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.blue_border_frame));
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.border_edit_text));
                tyeofuser="Male";

            }
        });


        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                female.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.pink_border_frame));
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.border_edit_text));
                tyeofuser="Female";

            }
        });

        calulateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tyeofuser.equals("0")){
                    Toast.makeText(BmiCalculator.this, "Select Your Gender First", Toast.LENGTH_SHORT).show();
                }else if(intprogress.equals("0")) {
                    Toast.makeText(BmiCalculator.this, "Select Your Height First", Toast.LENGTH_SHORT).show();
                } else if (intage==0 || intage<0) {
                    Toast.makeText(BmiCalculator.this, "Age is Invalid", Toast.LENGTH_SHORT).show();
                    
                } else if (intweight==0 || intweight<0) {
                    Toast.makeText(BmiCalculator.this, "Weight is Invalid", Toast.LENGTH_SHORT).show();

                }else {
                    Intent i= new Intent(BmiCalculator.this, BmiResult.class);

                    i.putExtra("gender",tyeofuser);
                    i.putExtra("height",intprogress);
                    i.putExtra("weight",weight2);
                    i.putExtra("age",age2);
                    startActivity(i);
                }

            }
        });




    }
}