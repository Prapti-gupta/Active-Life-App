package com.example.activelife;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WorkoutPlanning extends AppCompatActivity {

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private WorkoutAdapter workoutAdapter1;
    private WorkoutAdapter workoutAdapter2;
    private WorkoutAdapter workoutAdapter3;
    private WorkoutAdapter workoutAdapter4;
    private UserDatabaseHelper dbHelper;
    private ArrayList<Workout> workoutList = new ArrayList<>();
    private ArrayList<Workout> workoutList1 = new ArrayList<>();
    private ArrayList<Workout> workoutList2 = new ArrayList<>();
    private ArrayList<Workout> workoutList3 = new ArrayList<>();
    private ArrayList<Workout> workoutList4 = new ArrayList<>();
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_planning);

        dbHelper = new UserDatabaseHelper(this);
        recyclerView1 = findViewById(R.id.workout_recycler_view);
        recyclerView2 = findViewById(R.id.workout_recycler_view2);
        recyclerView3 = findViewById(R.id.workout_recycler_view3);
        recyclerView4 = findViewById(R.id.workout_recycler_view4);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));

        imageButton1 = findViewById(R.id.image_button1);
        imageButton2 = findViewById(R.id.image_button2);
        imageButton3 = findViewById(R.id.body1);
        imageButton4 = findViewById(R.id.body2);
        imageButton5 = findViewById(R.id.body3);
        imageButton6 = findViewById(R.id.body4);
        loadWorkouts();

        // Set the first adapter with the first half of the workouts using workout_item2.xml
        workoutAdapter1 = new WorkoutAdapter(
                WorkoutPlanning.this,
                workoutList1,
                selectedWorkout -> {
                    Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                    intent.putExtra("workoutId", selectedWorkout.getId());
                    startActivity(intent);
                },
                R.layout.workout_item2
        );
        recyclerView1.setAdapter(workoutAdapter1);   //layout it follows

        // Set the second adapter with the second half of the workouts using workout_item1.xml or any layout you prefer
        workoutAdapter2 = new WorkoutAdapter(
                WorkoutPlanning.this,
                workoutList2,
                selectedWorkout -> {
                    Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                    intent.putExtra("workoutId", selectedWorkout.getId());
                    startActivity(intent);
                },
                R.layout.workout_item //layout it follows
        );
        recyclerView2.setAdapter(workoutAdapter2);

        // Padding and Divider setup
        int spacingInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        recyclerView3.addItemDecoration(new SpacingItemDecoration(spacingInPixels));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView3.getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider)); // Use your drawable
        recyclerView3.addItemDecoration(dividerItemDecoration);


         workoutAdapter3 = new WorkoutAdapter(
                WorkoutPlanning.this,
                workoutList3,
                selectedWorkout -> {
                    Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                    intent.putExtra("workoutId", selectedWorkout.getId());
                    intent.putExtra("workoutName", selectedWorkout.getName());
                    startActivity(intent);
                },
                R.layout.workout_item3  //layout it follows
        );
        recyclerView3.setAdapter(workoutAdapter3);


        DividerItemDecoration dividerItemDecoration4 = new DividerItemDecoration(recyclerView4.getContext(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration4.setDrawable(getResources().getDrawable(R.drawable.divider)); // Use your drawable
        recyclerView4.addItemDecoration(dividerItemDecoration4);

        workoutAdapter4 = new WorkoutAdapter(
                WorkoutPlanning.this,
                workoutList4,
                selectedWorkout -> {
                    Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                    intent.putExtra("workoutId", selectedWorkout.getId());
                    intent.putExtra("workoutName",selectedWorkout.getName());
                    intent.putExtra("workoutTime",selectedWorkout.getTime());
                    intent.putExtra("workoutImg",selectedWorkout.getImageResId());
                    startActivity(intent);
                },
                R.layout.workout_item3  // Layout that recyclerView4 follows
        );
        recyclerView4.setAdapter(workoutAdapter4);



        imageButton1.setOnClickListener(v -> {
            if (!workoutList1.isEmpty()) {
                Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                intent.putExtra("workoutId", workoutList1.get(0).getId());
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(v -> {
            if (!workoutList2.isEmpty()) {
                Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                intent.putExtra("workoutId", workoutList3.get(0).getId());
                startActivity(intent);
            }
        });

        imageButton3.setOnClickListener(v -> {
            if (!workoutList2.isEmpty()) {
                Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                intent.putExtra("workoutId", workoutList4.get(0).getId());
                startActivity(intent);
            }
        });

        imageButton4.setOnClickListener(v -> {
            if (!workoutList2.isEmpty()) {
                Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                intent.putExtra("workoutId", workoutList2.get(0).getId());
                startActivity(intent);
            }
        });

        imageButton5.setOnClickListener(v -> {
            if (!workoutList2.isEmpty()) {
                Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                intent.putExtra("workoutId", workoutList1.get(0).getId());
                startActivity(intent);
            }
        });

        imageButton6.setOnClickListener(v -> {
            if (!workoutList2.isEmpty()) {
                Intent intent = new Intent(WorkoutPlanning.this, ExerciseActivity.class);
                intent.putExtra("workoutId", workoutList3.get(0).getId());
                startActivity(intent);
            }
        });




        ImageButton home = findViewById(R.id.homeButton);
        ImageButton meal = findViewById(R.id.mealButton);
        ImageButton workout = findViewById(R.id.workoutButton);
        ImageButton profile = findViewById(R.id.profileButton);

        home.setOnClickListener(view -> startActivity(new Intent(WorkoutPlanning.this, HomePage.class)));
        meal.setOnClickListener(view -> startActivity(new Intent(WorkoutPlanning.this, MealPlanning.class)));
        workout.setOnClickListener(view -> startActivity(new Intent(WorkoutPlanning.this, WorkoutPlanning.class)));
        profile.setOnClickListener(view -> startActivity(new Intent(WorkoutPlanning.this, Profile.class)));
    }

    private void loadWorkouts() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Query all workouts
            cursor = db.query(UserDatabaseHelper.TABLE_WORKOUT, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_ID);
                int nameIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_NAME);
                int imageIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_IMAGE);
                int timeIndex = cursor.getColumnIndex(UserDatabaseHelper.COLUMN_WORKOUT_TIME);

                //Loop through all workouts and add them to the workoutList
                do {
                    if (idIndex != -1 && nameIndex != -1 && imageIndex != -1 && timeIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        int imageResId = cursor.getInt(imageIndex);
                        String time = cursor.getString(timeIndex);

                        workoutList.add(new Workout(id, name, imageResId, time));
                    }
                } while (cursor.moveToNext());

                //Checks if there are enough workouts
                if (workoutList.size() >= 4) {
                    // Adds the first workout to workoutList1
                    workoutList1.add(workoutList.get(4));

                    workoutList2.add(workoutList.get(2));

                    workoutList3.add(workoutList.get(0));
                    workoutList3.add(workoutList.get(3));

                    workoutList4.add(workoutList.get(1));
                    workoutList4.add(workoutList.get(4));
                } else if (!workoutList.isEmpty()) {
                    //If there is only one workout, add it to all lists
                    workoutList1.add(workoutList.get(0));
                    workoutList2.add(workoutList.get(0));
                    workoutList3.add(workoutList.get(0));
                    workoutList4.add(workoutList.get(0));
                }
            }
        } catch (Exception e) {
            Log.e("DatabaseError", "Error loading workouts: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

}
