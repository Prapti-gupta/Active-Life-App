package com.example.activelife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ActiveLife.db";
    private static final int DATABASE_VERSION = 1;

    // User Credentials Table Columns
    public static final String TABLE_USER_CREDENTIALS = "user_credentials";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";

    // User Info Table Columns
    public static final String TABLE_USER_INFO = "user_info";
    public static final String COLUMN_INFO_ID = "info_id";
    public static final String COLUMN_USER_CITY = "city";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_AGE = "age";
    public static final String COLUMN_USER_ACTIVE_STATUS = "active_status";
    public static final String COLUMN_USER_HEIGHT = "height";
    public static final String COLUMN_USER_CURRENT_WEIGHT = "current_weight";
    public static final String COLUMN_USER_TARGET_WEIGHT = "target_weight";
    public static final String COLUMN_USER_MEDICAL_CONDITIONS = "medical_conditions";
    public static final String COLUMN_FOREIGN_KEY_USER_ID = "user_id"; // Foreign key column

    // Quotes Table Columns
    private static final String TABLE_QUOTES = "quotes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUOTE = "quote";

    //Workout Table Columns
    public static final String TABLE_WORKOUT = "workout";
    public static final String COLUMN_WORKOUT_ID = "id";
    public static final String COLUMN_WORKOUT_NAME = "name";
    public static final String COLUMN_WORKOUT_IMAGE = "image";
    public static final String COLUMN_WORKOUT_TIME = "time";

    //Exercise Table Columns
    public static final String TABLE_EXERCISE = "exercise";
    public static final String COLUMN_EXERCISE_ID = "exercise_id";
    public static final String COLUMN_EXERCISE_NAME = "exercise_name";
    public static final String COLUMN_WORKOUT_ID_FK = "workout_id";

    //Instruction Table Columns
    public static final String TABLE_INSTRUCTION = "instruction";
    public static final String COLUMN_INSTRUCTION_ID = "instruction_id";
    public static final String COLUMN_EXERCISE_NAME_FK = "exercise_name"; // FK
    public static final String COLUMN_EXERCISE_INSTRUCTIONS = "instructions";
    public static final String COLUMN_BREATHING_TIPS = "breathing_tips";
    public static final String COLUMN_FOCUS_AREAS = "focus_areas";
    public static final String COLUMN_COMMON_MISTAKES = "common_mistakes";

    //Workout Completed Table Columns
    public static final String TABLE_WORKOUTS_COMPLETED = "workouts_completed";
    public static final String COLUMN_COMPLETED_ID = "id"; // Unique identifier for the completed workout
    public static final String COLUMN_WORKOUT_COMPLETED_NAME = "workoutName"; // Name of the workout
    public static final String COLUMN_WORKOUT_DURATION = "workoutTime"; // Duration of the workout
    public static final String COLUMN_CALORIES_BURNED = "workoutCalorie"; // Calories burned
    public static final String COLUMN_WORKOUT_RATING = "workoutRating"; // Workout rating
    public static final String COLUMN_FOREIGN_KEY_USER_ID1 = "user_id"; // Foreign key column



    // Create Table SQL Statements
    private static final String CREATE_USER_CREDENTIALS_TABLE = "CREATE TABLE " + TABLE_USER_CREDENTIALS + " ("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_USER_EMAIL + " TEXT UNIQUE, "
            + COLUMN_USER_PASSWORD + " TEXT);";

    private static final String CREATE_USER_INFO_TABLE = "CREATE TABLE " + TABLE_USER_INFO + " ("
            + COLUMN_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FOREIGN_KEY_USER_ID + " INTEGER, "
            + COLUMN_USER_CITY + " TEXT, "
            + COLUMN_USER_GENDER + " TEXT, "
            + COLUMN_USER_AGE + " TEXT, "
            + COLUMN_USER_ACTIVE_STATUS + " TEXT, "
            + COLUMN_USER_HEIGHT + " TEXT, "
            + COLUMN_USER_CURRENT_WEIGHT + " TEXT, "
            + COLUMN_USER_TARGET_WEIGHT + " TEXT, "
            + COLUMN_USER_MEDICAL_CONDITIONS + " TEXT, "
            + "FOREIGN KEY(" + COLUMN_FOREIGN_KEY_USER_ID + ") REFERENCES " + TABLE_USER_CREDENTIALS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE);";


    private static final String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUOTE + " TEXT" + ")";


    private static final String createWorkoutTable = "CREATE TABLE " + TABLE_WORKOUT + " (" +
            COLUMN_WORKOUT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_WORKOUT_NAME + " TEXT, " +
            COLUMN_WORKOUT_IMAGE + " INTEGER, " +
            COLUMN_WORKOUT_TIME + " TEXT)";

    private static final String createExerciseTable = "CREATE TABLE " + TABLE_EXERCISE + " (" +
            COLUMN_EXERCISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EXERCISE_NAME + " TEXT, " +
            COLUMN_WORKOUT_ID_FK + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_WORKOUT_ID_FK + ") REFERENCES " +
            TABLE_WORKOUT + "(" + COLUMN_WORKOUT_ID + "))";

    private static final String createInstructionTable = "CREATE TABLE " + TABLE_INSTRUCTION + " (" +
            COLUMN_INSTRUCTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EXERCISE_NAME_FK + " TEXT, " +
            COLUMN_EXERCISE_INSTRUCTIONS + " TEXT, " +
            COLUMN_BREATHING_TIPS + " TEXT, " +
            COLUMN_FOCUS_AREAS + " TEXT, " +
            COLUMN_COMMON_MISTAKES + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_EXERCISE_NAME_FK + ") REFERENCES " +
            TABLE_EXERCISE + "(" + COLUMN_EXERCISE_NAME + "))";

    private static final String createWorkoutCompletedTable =
            "CREATE TABLE " + TABLE_WORKOUTS_COMPLETED + " ("
                    + COLUMN_COMPLETED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_FOREIGN_KEY_USER_ID1 + " INTEGER, "
                    + COLUMN_WORKOUT_COMPLETED_NAME + " TEXT, "
                    + COLUMN_WORKOUT_DURATION + " TEXT, "
                    + COLUMN_CALORIES_BURNED + " INTEGER, "
                    + COLUMN_WORKOUT_RATING + " INTEGER, "
                    + "FOREIGN KEY(" + COLUMN_FOREIGN_KEY_USER_ID1 + ") REFERENCES " + TABLE_USER_CREDENTIALS + "(" + COLUMN_USER_ID + ") ON DELETE CASCADE);";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_CREDENTIALS_TABLE);
        db.execSQL(CREATE_USER_INFO_TABLE);
        db.execSQL(CREATE_QUOTES_TABLE);
        db.execSQL(createWorkoutTable);
        db.execSQL(createExerciseTable);
        db.execSQL(createInstructionTable);
        db.execSQL(createWorkoutCompletedTable);

        // Insert initial quotes,sample workouts, exercises, exercises images and instructions
        insertInitialQuotes(db);
        insertSampleWorkouts(db);
        insertSampleExercises(db);
        insertSampleInstructions(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_CREDENTIALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INSTRUCTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKOUTS_COMPLETED);
        onCreate(db);
    }


   //Inserts user credentials from MainActivity
    public long insertUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USER_CREDENTIALS, null, values);
        db.close();
        return result;
    }


    // Inserts user information into user_info table
    public long insertUserInfo(long userId, String city, String gender, String age, String activity_level, String  height, String currentWeight, String targetWeight, String medicalConditions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOREIGN_KEY_USER_ID, userId); // Foreign key reference
        values.put(COLUMN_USER_CITY, city);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_AGE, age);
        values.put(COLUMN_USER_ACTIVE_STATUS, activity_level);
        values.put(COLUMN_USER_HEIGHT, height);
        values.put(COLUMN_USER_CURRENT_WEIGHT, currentWeight);
        values.put(COLUMN_USER_TARGET_WEIGHT, targetWeight);
        values.put(COLUMN_USER_MEDICAL_CONDITIONS, medicalConditions);
        long result = db.insert(TABLE_USER_INFO, null, values);
        db.close();
        return result;
    }

    public boolean checkUserCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER_CREDENTIALS + " WHERE " + COLUMN_USER_EMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean isValid = cursor.getCount() > 0; // If count > 0, credentials are valid
        cursor.close();
        db.close();
        return isValid;
    }


    public Cursor getUserInfo(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + TABLE_USER_CREDENTIALS + "." + COLUMN_USER_NAME + ", "
                + TABLE_USER_CREDENTIALS + "." + COLUMN_USER_EMAIL + ", "
                + TABLE_USER_INFO + "." + COLUMN_USER_AGE + ", "
                + TABLE_USER_INFO + "." + COLUMN_USER_HEIGHT + ", "
                + TABLE_USER_INFO + "." + COLUMN_USER_CURRENT_WEIGHT+ ", "
                + TABLE_USER_INFO + "." + COLUMN_USER_GENDER
                + " FROM " + TABLE_USER_CREDENTIALS
                + " INNER JOIN " + TABLE_USER_INFO
                + " ON " + TABLE_USER_CREDENTIALS + "." + COLUMN_USER_ID + " = " + TABLE_USER_INFO + "." + COLUMN_FOREIGN_KEY_USER_ID
                + " WHERE " + TABLE_USER_CREDENTIALS + "." + COLUMN_USER_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }




    //MOTIVATIONAL QUOTES TABLE
    private void insertInitialQuotes(SQLiteDatabase db) {
        String[] quotes = {
                "No pain, no gain. Shut up and train.",
                "Dream it. Wish it. Do it.",
                "Good things come to those who sweat.",
                "Progress, not perfection.",
                "Small steps every day.",
                "Stronger than yesterday.",
                "Sweat now, shine later.",
                "Make it happen.",
                "Keep pushing forward.",
                "You are your only limit.",
                "Today’s effort leads to tomorrow’s success.",
                "Results happen over time, not overnight.",
                "Believe you can and you’re halfway there.",
                "Start where you are. Use what you have.",
                "Fall in love with taking care of yourself.",
                "Every workout counts.",
                "Your only limit is you.",
                "Push harder than yesterday."
        };

        for (String quote : quotes) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_QUOTE, quote);
            db.insert(TABLE_QUOTES, null, values);
        }
    }

    public List<String> getAllQuotes() {
        List<String> quotesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_QUOTES, null);

        if (cursor.moveToFirst()) {
            do {
                quotesList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return quotesList;
    }





    //Workout table
    public void insertCompletedWorkout(String userId,int workoutId, String workoutName, String workoutTime, int workoutCalorie, int workoutRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_WORKOUT_COMPLETED_NAME, workoutName);
        values.put(COLUMN_WORKOUT_DURATION, workoutTime);
        values.put(COLUMN_CALORIES_BURNED, workoutCalorie);
        values.put(COLUMN_WORKOUT_RATING, workoutRating);
        values.put(COLUMN_FOREIGN_KEY_USER_ID1,userId);
        db.insert(TABLE_WORKOUTS_COMPLETED, null, values);
        db.close(); // Close the database after insertion
    }

    private void insertSampleWorkouts(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_WORKOUT + " (" + COLUMN_WORKOUT_NAME + ", " + COLUMN_WORKOUT_IMAGE + ", " + COLUMN_WORKOUT_TIME + ") VALUES ('Abs Beginner', " + R.drawable.twist2 + ", '45 minutes')");
        db.execSQL("INSERT INTO " + TABLE_WORKOUT + " (" + COLUMN_WORKOUT_NAME + ", " + COLUMN_WORKOUT_IMAGE + ", " + COLUMN_WORKOUT_TIME + ") VALUES ('Cardio Workout', " + R.drawable.stretching + ", '30 minutes')");
        db.execSQL("INSERT INTO " + TABLE_WORKOUT + " (" + COLUMN_WORKOUT_NAME + ", " + COLUMN_WORKOUT_IMAGE + ", " + COLUMN_WORKOUT_TIME + ") VALUES ('Strength Training', " + R.drawable.upperbodystrength + ", '20 minutes')");
        db.execSQL("INSERT INTO " + TABLE_WORKOUT + " (" + COLUMN_WORKOUT_NAME + ", " + COLUMN_WORKOUT_IMAGE + ", " + COLUMN_WORKOUT_TIME + ") VALUES ('Lower Body Stretching', " + R.drawable.yoga + ", '50 minutes')");
        db.execSQL("INSERT INTO " + TABLE_WORKOUT + " (" + COLUMN_WORKOUT_NAME + ", " + COLUMN_WORKOUT_IMAGE + ", " + COLUMN_WORKOUT_TIME + ") VALUES ('Full Body Workout', " + R.drawable.lowerbodyworkout + ", '40 minutes')");
    }

    private void insertSampleExercises(SQLiteDatabase db) {
        String[][] exercises = {
                {"Jumping Jacks", "Abdominal Crunches", "Russian Twist", "Mountain Climber", "Heel Touch", "Leg Raises", "Plank", "Cobra Stretch", "Stretch Left", "Stretch Right"},
                {"Jumping Jacks", "Incline Push-Ups", "Push-Ups", "Wide Arm Push-Ups", "Triceps Dips", "Knee Push-Ups", "Cobra Stretch"},
                {"Arm Raises", "Side Arm Raises", "Triceps Dips", "Arm Circles Clockwise", "Diamond Push-Ups", "Jumping Jacks", "Push-Ups", "Diagonal Plank"},
                {"Single Leg Hip Rotation", "Adductor Stretch", "Calf Stretch Left", "Calf Stretch Right", "Forward Bend", "Sitting Hamstring Stretch Left", "Sitting Hamstring Stretch Right"},
                {"Jumping Jacks", "Mountain Climber", "Triceps Dips", "Push-Ups", "Russian Twist", "Heel Touch", "Plank", "Cobra Stretch","Inchworm"}
        };

        db.beginTransaction();
        try {
            for (int workoutId = 1; workoutId <= exercises.length; workoutId++) {
                for (String exercise : exercises[workoutId - 1]) {
                    db.execSQL("INSERT INTO " + TABLE_EXERCISE + " (" + COLUMN_EXERCISE_NAME + ", " + COLUMN_WORKOUT_ID_FK + ") VALUES (?, ?)",
                            new Object[]{exercise, workoutId});
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("DB_ERROR", "Error inserting exercises: " + e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    private void insertSampleInstructions(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Jumping Jacks', 'Stand with feet together and arms at your sides. Jump while spreading your feet and raising your arms.', 'Breathe out as you jump, exhaling through your mouth to release tension. Inhale deeply through your nose as you land and return to the starting position. Maintain a steady breathing rhythm throughout the exercise to avoid fatigue.', '• Arms\n• Legs\n• Core\n• Back', 'Avoid landing too hard on your feet, which can strain your joints. Make sure to keep your knees slightly bent to reduce impact. Also, avoid shrugging your shoulders or letting your back round during the movement. Maintain a controlled motion to prevent injury.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Abdominal Crunches', 'Lie on your back with your knees bent, feet flat on the floor, and hands behind your head. Lift your shoulders and upper back towards your knees, engaging your core.', 'Exhale fully as you lift your shoulders, engaging your abdominal muscles. Inhale deeply as you lower your back to the floor in a controlled motion. Maintain steady, rhythmic breathing to support core engagement and avoid holding your breath.', '• Arms\n• Legs\n• Core\n• Back', 'Avoid pulling on your neck with your hands, which can strain your neck muscles. Focus on lifting with your core rather than your head or shoulders. Keep your movements slow and controlled to prevent momentum from taking over and reducing the effectiveness of the crunch.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Russian Twist', 'Sit on the floor with your knees bent and feet lifted. Twist your torso from side to side.', 'Exhale as you twist to each side, inhale when returning to the center.', '• Core\n• Obliques', 'Avoid using momentum. Engage your core to control the twist.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Mountain Climber', 'Start in a plank position. Alternate bringing each knee toward your chest in a running motion.', 'Exhale as you drive your knees in, inhale as you extend back.', '• Core\n• Arms\n• Legs', 'Avoid letting your hips sag. Keep your core engaged to maintain stability.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Heel Touch', 'Lie on your back with knees bent, feet flat. Reach your hands toward your heels side to side.', 'Exhale as you reach for each heel, inhale as you return to center.', '• Core\n• Obliques', 'Avoid using momentum to reach your heels. Keep the movement slow and controlled.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Leg Raises', 'Lie on your back with legs straight. Lift your legs up until they are perpendicular to the floor.', 'Exhale as you lift your legs, inhale as you lower them slowly.', '• Core\n• Lower Abs', 'Avoid arching your lower back. Keep your core engaged throughout.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Plank', 'Start in a push-up position, forearms on the floor, and hold your body straight.', 'Breathe steadily. Do not hold your breath. Maintain a steady exhale.', '• Core\n• Arms\n• Shoulders', 'Avoid letting your hips sag or raising them too high. Keep your body in a straight line.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Cobra Stretch', 'Lie face down and press your upper body up with your hands, arching your back.', 'Inhale deeply as you lift, exhale slowly as you lower back down.', '• Lower Back\n• Core', 'Avoid overextending your back. Keep the movement gentle, especially if you have back issues.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Stretch Left', 'Stand with feet together. Reach your left arm over your head and stretch to the right.', 'Inhale deeply as you stretch, exhale as you release the stretch.', '• Side Body\n• Arms', 'Avoid overstretching. Keep the stretch gentle and controlled.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Stretch Right', 'Stand with feet together. Reach your right arm over your head and stretch to the left.', 'Inhale deeply as you stretch, exhale as you release the stretch.', '• Side Body\n• Arms', 'Avoid overstretching. Keep the stretch gentle and controlled.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Incline Push-Ups', 'Place your hands on a bench or platform. Lower your chest towards the platform, then push back up.', 'Inhale as you lower your chest, exhale as you push up.', '• Chest\n• Shoulders\n• Arms', 'Avoid letting your hips sag or raising them too high. Keep a straight body line.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Push-Ups', 'Start in a plank position. Lower your chest to the floor and push back up.', 'Inhale as you lower your body, exhale as you push up.', '• Chest\n• Shoulders\n• Triceps', 'Avoid flaring your elbows out. Keep your body in a straight line.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Wide Arm Push-Ups', 'Start in a plank position with your hands wider than shoulder-width. Lower your chest and push up.', 'Inhale as you lower, exhale as you push up.', '• Chest\n• Shoulders\n• Arms', 'Avoid letting your hips sag. Keep your core engaged and body aligned.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Triceps Dips', 'Sit on the edge of a chair, hands gripping the edge. Lower your body by bending your elbows, then push back up.', 'Inhale as you lower, exhale as you push up.', '• Triceps\n• Shoulders\n• Arms', 'Avoid using your legs to push up. Focus on your arms doing the work.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Knee Push-Ups', 'Start in a plank position with your knees on the floor. Lower your chest and push up.', 'Inhale as you lower your chest, exhale as you push up.', '• Chest\n• Arms\n• Core', 'Avoid arching your lower back. Keep your body aligned.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Arm Raises', 'Stand with feet shoulder-width apart. Lift your arms straight out to your sides until they are parallel to the floor.', 'Inhale as you lift your arms, exhale as you lower them.', '• Shoulders\n• Arms', 'Avoid shrugging your shoulders. Keep the movement controlled.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Side Arm Raises', 'Stand with feet shoulder-width apart. Raise your arms to the side until they are parallel to the ground.', 'Inhale as you raise your arms, exhale as you lower them.', '• Shoulders\n• Arms', 'Avoid swinging your arms. Keep the movement slow and controlled.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Arm Circles Clockwise', 'Stand with arms extended out to the sides. Rotate your arms in small circles in a clockwise direction.', 'Breathe steadily throughout the movement.', '• Shoulders\n• Arms', 'Avoid rotating too quickly. Keep your movements controlled.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Diamond Push-Ups', 'Start in a push-up position with your hands forming a diamond shape beneath your chest. Lower your body and push back up.', 'Inhale as you lower, exhale as you push up.', '• Triceps\n• Chest\n• Shoulders', 'Avoid flaring your elbows out. Keep your body in a straight line.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Diagonal Plank', 'Start in a standard plank position, then lift one arm and the opposite leg off the ground. Hold, then switch sides.', 'Breathe steadily while maintaining your balance.', '• Core\n• Shoulders\n• Glutes', 'Avoid letting your hips twist or sag. Keep your core engaged and balanced.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Single Leg Hip Rotation', 'Stand on one leg and rotate your raised knee outward, then back inward.', 'Breathe deeply as you move your leg in and out.', '• Hips\n• Balance', 'Avoid twisting your entire body. Keep the rotation focused on your hip joint.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Adductor Stretch', 'Sit on the floor with your feet together, knees bent. Gently press your knees down with your hands.', 'Inhale deeply as you hold the stretch, exhale slowly.', '• Groin\n• Hips', 'Avoid bouncing in the stretch. Hold the position gently and consistently.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Calf Stretch Left', 'Stand facing a wall, with your left leg extended behind you. Push your left heel into the ground.', 'Inhale as you position yourself, exhale deeply as you hold the stretch.', '• Calves\n• Ankles', 'Avoid bouncing in the stretch. Keep your heel pressed into the ground.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Calf Stretch Right', 'Stand facing a wall, with your right leg extended behind you. Push your right heel into the ground.', 'Inhale as you position yourself, exhale deeply as you hold the stretch.', '• Calves\n• Ankles', 'Avoid bouncing in the stretch. Keep your heel pressed into the ground.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Inchworm', 'Stand tall, then bend at the waist and walk your hands forward into a plank position. Hold for a moment, then walk your feet towards your hands.', 'Inhale as you reach forward, exhale as you walk your feet back to standing.', '• Shoulders\n• Core\n• Hamstrings', 'Avoid rounding your back. Keep your core engaged throughout the movement.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Forward Bend', 'Stand with feet hip-width apart, hinge at the hips, and bend forward, keeping a slight bend in your knees. Reach towards the ground.', 'Inhale as you lengthen your spine, exhale as you fold deeper into the stretch.', '• Hamstrings\n• Lower Back', 'Avoid locking your knees. Keep your spine elongated during the movement.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Sitting Hamstring Stretch Left', 'Sit with your left leg extended and right foot against your inner left thigh. Reach towards your left foot while keeping your back straight.', 'Inhale as you sit tall, exhale as you reach towards your foot.', '• Hamstrings\n• Lower Back', 'Avoid rounding your back. Keep your knee of the extended leg straight.');");

        db.execSQL("INSERT INTO " + TABLE_INSTRUCTION + " (" + COLUMN_EXERCISE_NAME_FK + ", " +
                COLUMN_EXERCISE_INSTRUCTIONS + ", " + COLUMN_BREATHING_TIPS + ", " +
                COLUMN_FOCUS_AREAS + ", " + COLUMN_COMMON_MISTAKES + ") VALUES " +
                "('Sitting Hamstring Stretch Right', 'Sit with your right leg extended and left foot against your inner right thigh. Reach towards your right foot while keeping your back straight.', 'Inhale as you sit tall, exhale as you reach towards your foot.', '• Hamstrings\n• Lower Back', 'Avoid rounding your back. Keep your knee of the extended leg straight.');");


    }

    public Cursor getExerciseDetails(String exerciseName) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_INSTRUCTION, null, COLUMN_EXERCISE_NAME_FK + " = ?", new String[]{exerciseName}, null, null, null);
    }
    public Cursor getExercisesByWorkoutId(int workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_EXERCISE + " WHERE " + COLUMN_WORKOUT_ID_FK + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(workoutId)});
    }

    public Cursor getWorkoutById(int workoutId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_WORKOUT + " WHERE " + COLUMN_WORKOUT_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(workoutId)});
    }


}