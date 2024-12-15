package com.example.activelife;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.net.Uri;
import android.widget.VideoView;
import android.widget.ImageView; // Import ImageView
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class ExerciseDetailActivity extends AppCompatActivity {
    private TextView textViewExerciseName;
    private TextView textViewInstructions;
    private TextView textViewBodyParts;
    private TextView textViewBreathingTips;
    private TextView textViewCommonMistakes;
    private UserDatabaseHelper dbHelper;
    private String exerciseName;
    private VideoView videoView;
    private ImageView imageFocusArea; // Declare ImageView for focus area image

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        textViewExerciseName = findViewById(R.id.text_view_exercise_name);
        textViewInstructions = findViewById(R.id.text_view_instructions);
        textViewBodyParts = findViewById(R.id.text_view_body_parts);
        textViewBreathingTips = findViewById(R.id.text_view_breathing_tips);
        textViewCommonMistakes = findViewById(R.id.text_view_common_mistakes);
        videoView = findViewById(R.id.video_view);



        dbHelper = new UserDatabaseHelper(this);

        if (getIntent().hasExtra("exerciseName")) {
            exerciseName = getIntent().getStringExtra("exerciseName");
            loadExerciseDetails(exerciseName);
            loadVideoForExercise(exerciseName);

        }
    }

    private void loadExerciseDetails(String exerciseName) {
        Cursor cursor = null;
        try {
            cursor = dbHelper.getExerciseDetails(exerciseName);

            if (cursor != null && cursor.moveToFirst()) {
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_EXERCISE_INSTRUCTIONS));
                String breathingTips = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_BREATHING_TIPS));
                String focusAreas = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_FOCUS_AREAS));
                String commonMistakes = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_COMMON_MISTAKES));

                // Retrieve image resource ID from the database

                // Set text in the corresponding TextViews
                textViewExerciseName.setText(exerciseName);
                textViewInstructions.setText(instructions);
                textViewBodyParts.setText(focusAreas);
                textViewBreathingTips.setText(breathingTips);
                textViewCommonMistakes.setText(commonMistakes);

                // Set the image resource to the ImageView below focus area

            } else {
                // If no data is found, handle it appropriately
                Log.e("ExerciseDetail", "No details found for the exercise: " + exerciseName);
            }
        } catch (Exception e) {
            Log.e("ExerciseDetail", "Error retrieving exercise details: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();  // Ensure cursor is closed to avoid memory leaks
            }
        }
    }

    private void loadVideoForExercise(String exerciseName) {
        int videoResId;
        switch (exerciseName) {
            case "Jumping Jacks":
                videoResId = R.raw.jumping_jacks;
                break;
            case "Abdominal Crunches":
                videoResId = R.raw.abdominal_crunches;
                break;
            case "Russian Twist":
                videoResId = R.raw.russian_twist;
                break;
            case "Mountain Climber":
                videoResId = R.raw.mountain_climber;
                break;
            case "Heel Touch":
                videoResId = R.raw.heel_touch;
                break;
            case "Inchworm":
                videoResId = R.raw.inchworms;
                break;
            case "Leg Raises":
                videoResId = R.raw.leg_raise;
                break;
            case "Plank":
                videoResId = R.raw.diagonal_plank;
                break;
            case "Cobra Stretch":
                videoResId = R.raw.cobra_pose;
                break;
            case "Stretch Left":
                videoResId = R.raw.stretch_left;
                break;
            case "Stretch Right":
                videoResId = R.raw.stretch_right;
                break;
            case "Incline Push-Ups":
                videoResId = R.raw.incline_pushup;
                break;
            case "Push-Ups":
                videoResId = R.raw.pushup;
                break;
            case "Wide Arm Push-Ups":
                videoResId = R.raw.pushup;
                break;
            case "Triceps Dips":
                videoResId = R.raw.tricep_dips;
                break;
            case "Knee Push-Ups":
                videoResId = R.raw.knee_pushup;
                break;
            case "Arm Raises":
                videoResId = R.raw.arm_raises;
                break;
            case "Side Arm Raises":
                videoResId = R.raw.knee_pushup;
                break;
            case "Arm Circles Clockwise":
                videoResId = R.raw.arm_circles;
                break;
            case "Single Leg Hip Rotation":
                videoResId = R.raw.single_leg_hip_rotation;
                break;
            case "Adductor Stretch":
                videoResId = R.raw.adductor_stretch;
                break;
            case "Calf Stretch Left":
                videoResId = R.raw.calf_stretch_left;
                break;
            case "Calf Stretch Right":
                videoResId = R.raw.calf_stretch_right;
                break;
            case "Forward Bend":
                videoResId = R.raw.forward_bend;
                break;
            case "Sitting Hamstring Stretch Left":
                videoResId = R.raw.sitting_hamstring_left;
                break;
            case "Sitting Hamstring Stretch Right":
                videoResId = R.raw.sitting_hamstring_right;
                break;
            case "Diamond Push-Ups":
                videoResId = R.raw.diamond_pushup;
                break;
            case "Diagonal Plank":
                videoResId = R.raw.diagonal_plank;
                break;
            default:
                videoResId = R.raw.jumping_jacks; // Default video if no specific match
                break;
        }

        try {
            Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
            videoView.setVideoURI(videoUri);
            videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop the video
            videoView.start();
        } catch (Exception e) {
            Log.e("ExerciseDetail", "Error loading video: " + e.getMessage());
        }
    }
}
