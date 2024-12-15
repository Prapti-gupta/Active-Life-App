package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MealPlanning extends AppCompatActivity {

    String mealPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_planning);
        ImageButton home = (ImageButton)findViewById(R.id.homeButton);
        ImageButton meal = (ImageButton)findViewById(R.id.mealButton);
        ImageButton workout = (ImageButton)findViewById(R.id.workoutButton);
        ImageButton profile = (ImageButton)findViewById(R.id.profileButton);


        LinearLayout weightloss=(LinearLayout)findViewById(R.id.ll_weightlossplan);
        LinearLayout musclegain=(LinearLayout)findViewById(R.id.ll_musclegainplan);
        LinearLayout maintain=(LinearLayout)findViewById(R.id.ll_maintenanceplan);
        LinearLayout vegan=(LinearLayout)findViewById(R.id.ll_veganplan);
        LinearLayout gluten=(LinearLayout)findViewById(R.id.ll_glutenfreeplan);


        weightloss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlan="Weight Loss Plan";
                Intent intent = new Intent(MealPlanning.this, MealPlanDisplay.class);
                intent.putExtra("mealPlan", mealPlan);
                startActivity(intent);

            }
        });

        musclegain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlan="Muscle Gain Plan";
                Intent intent = new Intent(MealPlanning.this, MealPlanDisplay.class);
                intent.putExtra("mealPlan", mealPlan);
                startActivity(intent);

            }
        });

        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlan="Maintenance Plan";
                Intent intent = new Intent(MealPlanning.this, MealPlanDisplay.class);
                intent.putExtra("mealPlan", mealPlan);
                startActivity(intent);
            }
        });

        vegan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlan="Vegan Plan";

                Intent intent = new Intent(MealPlanning.this, MealPlanDisplay.class);
                intent.putExtra("mealPlan", mealPlan);
                startActivity(intent);
            }
        });

        gluten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealPlan="Gluten Free Plan";

                Intent intent = new Intent(MealPlanning.this, MealPlanDisplay.class);
                intent.putExtra("mealPlan", mealPlan);
                startActivity(intent);
            }
        });




        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealPlanning.this, HomePage.class);
                startActivity(i);
            }
        });

        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealPlanning.this, MealPlanning.class);
                startActivity(i);
            }
        });

        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealPlanning.this, WorkoutPlanning.class);
                startActivity(i);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealPlanning.this, Profile.class);
                startActivity(i);
            }
        });
    }
}