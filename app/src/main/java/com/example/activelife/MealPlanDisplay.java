package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MealPlanDisplay extends AppCompatActivity {

    private TextView Breakfast, Lunch, Snack, Dinner, Description,MealPlanName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_display);

        Breakfast = findViewById(R.id.descriptionBreakfast);
        Lunch = findViewById(R.id.descriptionLunch);
        Snack = findViewById(R.id.descriptionSnacks);
        Dinner = findViewById(R.id.descriptionDinner);
        Description = findViewById(R.id.mealPlanDescription);
        MealPlanName = findViewById(R.id.mealPlanName);


        ImageButton back=(ImageButton) findViewById(R.id.back_button);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MealPlanDisplay.this, MealPlanning.class);
                startActivity(i);
            }
        });

        String mealPlan = getIntent().getStringExtra("mealPlan");

        getMealPlanDetails(mealPlan);
    }

    private void getMealPlanDetails(String mealPlan) {
        switch (mealPlan) {
            case "Weight Loss Plan":
                MealPlanName.setText("Weight Loss Plan");
                Description.setText("Low-calorie, nutrient-rich foods, focused on portion control and balanced nutrition.");
                Breakfast.setText("Greek yogurt with a handful of berries and a sprinkle of chia seeds");
                Lunch.setText("Grilled chicken salad with mixed greens, tomatoes, cucumbers, and olive oil dressing");
                Snack.setText("Sliced apple with a tablespoon of almond butter");
                Dinner.setText("Baked salmon with steamed broccoli and a small sweet potato");
                break;

            case "Muscle Gain Plan":
                MealPlanName.setText("Muscle Gain Plan");
                Description.setText("Higher protein intake with a mix of complex carbs and healthy fats for muscle growth and recovery.\n");
                Breakfast.setText("Scrambled eggs with spinach, whole grain toast, and a banana");
                Lunch.setText("Grilled turkey wrap with whole grain tortilla, avocado, lettuce, and a side of quinoa");
                Snack.setText("Protein smoothie with a scoop of protein powder, almond milk, and a handful of nuts\n");
                Dinner.setText("Beef stir-fry with bell peppers, onions, and a serving of brown rice");
                break;

            case "Maintenance Plan":
                MealPlanName.setText("Maintenance Plan");
                Description.setText("Balanced meals to support overall health and energy levels without specific weight goals.\n");
                Breakfast.setText("Oatmeal with a spoon of peanut butter, sliced banana, and a dash of cinnamon");
                Lunch.setText("Hummus and veggie wrap with whole grain wrap, cucumbers, carrots, and lettuce");
                Snack.setText("Greek yogurt with a handful of mixed nuts");
                Dinner.setText("Grilled chicken breast, mixed roasted vegetables, and a serving of couscous");
                break;

            case "Vegan Plan":
                MealPlanName.setText("Vegan Plan");
                Description.setText("Plant-based meals high in nutrients, fiber, and healthy fats.");
                Breakfast.setText("Smoothie bowl with blended spinach, frozen berries, almond milk, and topped with chia seeds, coconut flakes, and sliced banana");
                Lunch.setText("Chickpea and vegetable stir-fry with quinoa, bell peppers, zucchini, and a drizzle of tahini dressing");
                Snack.setText("Hummus with carrot sticks and cucumber slices");
                Dinner.setText("Lentil and vegetable stew with carrots, celery, potatoes, and a side of whole-grain bread");
                break;

            case "Gluten Free Plan":
                MealPlanName.setText("Gluten Free Plan");
                Description.setText("Gluten-free meals focusing on whole foods and avoiding wheat products.");
                Breakfast.setText("SGreek yogurt parfait with honey, gluten-free granola, and fresh blueberries");
                Lunch.setText("Grilled chicken and avocado salad with mixed greens, cherry tomatoes, and olive oil dressing");
                Snack.setText("Mixed nuts and a small handful of grapes");
                Dinner.setText("Baked sweet potato stuffed with black beans, corn, and salsa, topped with shredded cheese");
                break;

            default:
                Description.setText("No meal plan selected.");
                Breakfast.setText("");
                Lunch.setText("");
                Snack.setText("");
                Dinner.setText("");
                break;
        }
    }
}
