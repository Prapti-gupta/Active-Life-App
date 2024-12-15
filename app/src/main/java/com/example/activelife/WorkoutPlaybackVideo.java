package com.example.activelife;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Random;

public class WorkoutPlaybackVideo extends AppCompatActivity {
    private VideoView videoView;
    private TextView timerTextView;
    private TextView exerciseNameTextView;
    private View breakOverlay;
    private TextView breakTimeTextView;
    private TextView breakTime;
    private ArrayList<String> exerciseList;
    private int currentExerciseIndex = 0;
    private Handler handler = new Handler();
    private CountDownTimer countDownTimer;
    private static final int EXERCISE_DURATION = 15; // 15 seconds for each exercise
    private static final int BREAK_INTERVAL = 4; // After every 4 exercises, take a break
    private static final int BREAK_DURATION = 10;
    private boolean isBreakTime = false;
    private int remainingTimeInSeconds = EXERCISE_DURATION; // Track remaining time in seconds
    private int breakCount = 0; //Tracks exercises completed since last break

    // Overlay components
    private RelativeLayout quitOverlay;
    private Button quitButton;
    private Button resumeButton;
    private Button skipButton;
    private int workoutId,workoutImg;
    private CircularTimerView circularTimerView; // Circular timer view
    private String workoutTime,workoutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_playback_video);

        // Retrieve the exercise list from the intent
        exerciseList = getIntent().getStringArrayListExtra("exerciseList");

        // Initialize VideoView, Timer TextView, Exercise Name TextView, Break Overlay
        videoView = findViewById(R.id.videoView);
        timerTextView = findViewById(R.id.timerTextView);
        exerciseNameTextView = findViewById(R.id.exerciseNameTextView);
        breakOverlay = findViewById(R.id.breakOverlay);
        breakTimeTextView = findViewById(R.id.breakTimeTextView);
        breakTime = findViewById(R.id.breakTime);
        breakOverlay.setVisibility(View.GONE);

        // Initialize quit overlay and buttons
        quitOverlay = findViewById(R.id.quit_overlay);
        quitButton = findViewById(R.id.quit_button);
        resumeButton = findViewById(R.id.resume_button);
        skipButton = findViewById(R.id.skip_button);
        quitOverlay.setVisibility(View.GONE);

        // Initialize circular timer view
        circularTimerView = findViewById(R.id.circularTimerView);
        circularTimerView.setTotalTime(EXERCISE_DURATION);

        workoutId = getIntent().getIntExtra("workoutId", 1);
        workoutName = getIntent().getStringExtra("workoutName");
        workoutTime = getIntent().getStringExtra("workoutTime");
        workoutImg = getIntent().getIntExtra("workoutImg",1);
        if (exerciseList != null && !exerciseList.isEmpty()) {
            playNextExercise();
        } else {
            Log.e("WorkoutPlaybackVideo", "Exercise list is empty or null.");
        }

        resumeButton.setOnClickListener(v -> {
            quitOverlay.setVisibility(View.GONE);
            skipButton.setVisibility(View.VISIBLE);
            circularTimerView.startTimer();
            startExerciseTimer(remainingTimeInSeconds); // Resume timer from remaining time
            resumeVideo(); // Ensure the video resumes playback
        });

        // Set up quit button listener to close the activity
        quitButton.setOnClickListener(v -> {
            videoView.stopPlayback();
            circularTimerView.stopTimer();
            finish();
        });

        // Skip button listener
        skipButton.setOnClickListener(v -> skipToNextExercise());
    }

    @Override
    public void onBackPressed() {
        quitOverlay.setVisibility(View.VISIBLE);
        skipButton.setVisibility(View.GONE);
        videoView.pause(); // Pause the video
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the countdown timer
        }
        circularTimerView.stopTimer();
    }

    private void startExerciseTimer(int seconds) {
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) { // Only to handle tick intervals
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInSeconds--; // Decrement by 1 second
                timerTextView.setText(String.valueOf(remainingTimeInSeconds)); // Update timer display
                circularTimerView.setElapsedTime(EXERCISE_DURATION - remainingTimeInSeconds); // Update circular timer
            }

            @Override
            public void onFinish() {
                videoView.stopPlayback();
                circularTimerView.stopTimer();
                handleEndOfExercise();
            }
        }.start();
    }

    private void playNextExercise() {
        if (currentExerciseIndex < exerciseList.size()) {
            //Checks if it's time for a break
            if (breakCount == BREAK_INTERVAL) {
                startBreak();
                breakCount = 0; //Resets count after break
            } else {
                // Play the next exercise video
                String exerciseName = exerciseList.get(currentExerciseIndex);
                exerciseNameTextView.setText(exerciseName);
                int videoResId = getVideoResourceByName(exerciseName);

                // Reset remaining time for the new exercise
                remainingTimeInSeconds = EXERCISE_DURATION;
                timerTextView.setText(String.valueOf(remainingTimeInSeconds)); //Updates timer display
                circularTimerView.setTotalTime(EXERCISE_DURATION); //Resets circular timer

                startExerciseTimer(EXERCISE_DURATION); //Starts the timer for the new exercise

                if (videoResId != 0) {
                    Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + videoResId);
                    videoView.setVideoURI(videoUri);
                    videoView.setOnPreparedListener(mp -> {
                        mp.setLooping(true);
                        videoView.start(); // Start video playback
                    });
                } else {
                    Log.e("VideoPlayback", "No video found for exercise: " + exerciseName);
                }
            }
        } else {
            Log.i("WorkoutPlaybackVideo", "All exercises completed.");

           int caloriesBurnt = new Random().nextInt(50) + 100; // Random calories between 100 and 150

            // Navigate to WorkoutCompleted activity and pass the details
            Intent intent = new Intent(WorkoutPlaybackVideo.this, WorkoutCompleted.class);
            intent.putExtra("workoutId", workoutId); // Replace with actual ID if available
            intent.putExtra("workoutName", workoutName); // Replace with actual workout name
            intent.putExtra("exerciseCount", exerciseList.size());
            intent.putExtra("workoutTime", workoutTime);
            intent.putExtra("caloriesBurnt", caloriesBurnt);
            startActivity(intent);
            finish();

        }
    }

    private void startBreak() {
        isBreakTime = true;
        breakOverlay.setVisibility(View.VISIBLE);
        skipButton.setVisibility(View.GONE);
        breakTimeTextView.setVisibility(View.VISIBLE);
        breakTime.setVisibility(View.VISIBLE);
        breakTimeTextView.setText(String.valueOf(BREAK_DURATION));

        //Start break countdown
        new CountDownTimer(BREAK_DURATION * 1000, 1000) {
            int remainingBreakTime = BREAK_DURATION;

            @Override
            public void onTick(long millisUntilFinished) {
                remainingBreakTime--;
                breakTimeTextView.setText(String.valueOf(remainingBreakTime));
            }

            @Override
            public void onFinish() {
                endBreak();
            }
        }.start();
    }

    private void endBreak() {
        isBreakTime = false;
        breakOverlay.setVisibility(View.GONE);
        skipButton.setVisibility(View.VISIBLE);
        breakTimeTextView.setVisibility(View.GONE);
        breakTime.setVisibility(View.GONE);
        videoView.pause(); // Pause the video before transitioning
        handler.postDelayed(this::playNextExercise, 500); // Delay the transition by 500ms for smoothness

    }

    private void handleEndOfExercise() {
        currentExerciseIndex++;
        breakCount++;
        videoView.pause();
        handler.postDelayed(this::playNextExercise, 500);
    }
    private void skipToNextExercise() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        handleEndOfExercise();
    }

    private void resumeVideo() {
        if (videoView != null) {
            videoView.start();
        }
    }
    private int getVideoResourceByName(String exerciseName) {
        switch (exerciseName) {
            case "Jumping Jacks":
                return R.raw.jumping_jacks;

            case "Abdominal Crunches":
                return R.raw.abdominal_crunches;

            case "Russian Twist":
                return R.raw.russian_twist;

            case "Mountain Climber":
                return R.raw.mountain_climber;

            case "Heel Touch":
                return R.raw.heel_touch;

            case "Inchworm":
                return R.raw.inchworms;

            case "Leg Raises":
                return R.raw.leg_raise;

            case "Plank":
                return R.raw.diagonal_plank;

            case "Cobra Stretch":
                return R.raw.cobra_pose;

            case "Stretch Left":
                return R.raw.stretch_left;

            case "Stretch Right":
                return R.raw.stretch_right;

            case "Incline Push-Ups":
                return R.raw.incline_pushup;

            case "Push-Ups":
                return R.raw.pushup;

            case "Wide Arm Push-Ups":
                return R.raw.pushup;

            case "Triceps Dips":
                return R.raw.tricep_dips;

            case "Knee Push-Ups":
                return R.raw.knee_pushup;

            case "Arm Raises":
                return R.raw.arm_raises;

            case "Side Arm Raises":
                return R.raw.sidearm_raises;

            case "Arm Circles Clockwise":
                return R.raw.arm_circles;

            case "Single Leg Hip Rotation":
                return R.raw.single_leg_hip_rotation;

            case "Adductor Stretch":
                return R.raw.adductor_stretch;

            case "Calf Stretch Left":
                return R.raw.calf_stretch_left;

            case "Calf Stretch Right":
                return R.raw.calf_stretch_right;

            case "Forward Bend":
                return R.raw.forward_bend;

            case "Sitting Hamstring Stretch Left":
                return R.raw.sitting_hamstring_left;

            case "Sitting Hamstring Stretch Right":
                return R.raw.sitting_hamstring_right;

            case "Diamond Push-Ups":
                return R.raw.diamond_pushup;

            case "Diagonal Plank":
                return R.raw.diagonal_plank;

            default:
                return 0;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        circularTimerView.stopTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        circularTimerView.startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.stopPlayback();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}