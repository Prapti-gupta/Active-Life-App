package com.example.activelife;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BmiResult extends AppCompatActivity {

    Button resultCalculateBmi;
    TextView bmidisplay,bmicategory,gender;
    Intent i;
    String bmi;
    ImageView imageview;
    float intbmi;
    String height,weight;
    float intheight,intweight;
    RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);

        Button gotoprofile= (Button) findViewById(R.id.gotoprofile);
        Button gotomain= (Button) findViewById(R.id.gotomain);


        i=getIntent();
        bmidisplay=findViewById(R.id.bmidisplay);
        bmicategory=findViewById(R.id.bmicategorydisplay);
        gender=findViewById(R.id.genderdisplay);
        background=findViewById(R.id.contentlayout);
        imageview=findViewById(R.id.imageview);

        height= i.getStringExtra("height");
        weight=i.getStringExtra("weight");

        intheight=Float.parseFloat(height);
        intweight=Float.parseFloat(weight);

        intheight=intheight/100; //converted to meter

        intbmi=intweight/(intheight*intheight);  //kg/m^2

        bmi=Float.toString(intbmi);







        if(intbmi<16){
            bmicategory.setText("Severe Thinness");
            background.setBackgroundColor(Color.parseColor("#FFCDD2"));
            imageview.setImageResource(R.drawable.crosss);


        }else if(intbmi<16.9 && intbmi>16){
            bmicategory.setText("Moderate Thinness");
            background.setBackgroundColor(Color.parseColor("#FFCDD2"));
            imageview.setImageResource(R.drawable.crosss);

        }else if(intbmi<18.4 && intbmi>17){
            bmicategory.setText("Mild Thinness");
            background.setBackgroundColor(Color.parseColor("#FFCDD2"));
            imageview.setImageResource(R.drawable.crosss);

        }else if(intbmi<25 && intbmi>18.4){
            bmicategory.setText("Normal");
            background.setBackgroundColor(Color.parseColor("#C8E6C9"));
            imageview.setImageResource(R.drawable.ok);

        }else if(intbmi<29.5 && intbmi>25){
            bmicategory.setText("Overweight");
            background.setBackgroundColor(Color.parseColor("#FFF9C4"));
            imageview.setImageResource(R.drawable.warning);

        }else{
            bmicategory.setText("Obese Class I");
            background.setBackgroundColor(Color.parseColor("#FFF9C4"));
            imageview.setImageResource(R.drawable.warning);

        }


        gender.setText(i.getStringExtra("gender"));
        bmidisplay.setText(bmi);






        gotoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BmiResult.this,Profile.class);
                startActivity(i);
                finish();
            }
        });

        gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BmiResult.this,BmiCalculator.class);
                startActivity(i);
                finish();

            }
        });

    }
}