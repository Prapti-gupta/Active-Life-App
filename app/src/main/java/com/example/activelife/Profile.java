package com.example.activelife;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Profile extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION_CODE=1;

    private static final int REQUEST_IMAGE_CAPTURE=2;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ImageButton edit =(ImageButton)findViewById(R.id.edit_profile);
        ImageButton home = (ImageButton)findViewById(R.id.homeButton);
        ImageButton meal = (ImageButton)findViewById(R.id.mealButton);
        ImageButton workout = (ImageButton)findViewById(R.id.workoutButton);
        ImageButton profile = (ImageButton)findViewById(R.id.profileButton);
        Button view_progress_album = (Button)findViewById(R.id.view_progress_album);
        Button calculate_bmi=(Button)findViewById(R.id.calculate_bmi);
        Button change_step_target=(Button)findViewById(R.id.change_step_target);
        Button achievements=(Button)findViewById(R.id.view_achivements);
        Button contact_trainer=(Button)findViewById(R.id.contact_trainer);

        ImageView profile_picture=(ImageView)findViewById(R.id.profile_picture);


        TextView username=(TextView)findViewById(R.id.user_name);
        TextView email=(TextView)findViewById(R.id.user_email);
        TextView weight=(TextView)findViewById(R.id.myweight);
        TextView height=(TextView)findViewById(R.id.myheight);
        TextView age=(TextView)findViewById(R.id.myage);

        Button logout=(Button)findViewById(R.id.logout);



        // Retrieve the user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);

        if (userId != -1) {
            UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);
            Cursor cursor = dbHelper.getUserInfo(userId);

            if (cursor != null && cursor.moveToFirst()) {
                // Extract data from cursor
                String name = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_NAME));
                String emailText = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_EMAIL));
                String ageValue = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_AGE));
                String heightValue = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_HEIGHT));
                String weightValue = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_CURRENT_WEIGHT));
                String genderValue = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_GENDER));


                // Set data to TextViews
                username.setText(name);
                email.setText(emailText);
                age.setText(ageValue);
                height.setText(heightValue);
                weight.setText(weightValue);

                if ("Female".equalsIgnoreCase(genderValue)) {
                    profile_picture.setImageResource(R.drawable.femaleprofile); // Replace with your female image resource
                }



                cursor.close();
            } else {
                Toast.makeText(this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }





        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, EditProfile.class);
                startActivity(i);
            }
        });


        view_progress_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this,ProgressAlbum.class);
                startActivity(i);

            }
        });

        calculate_bmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this,BmiCalculator.class);
                startActivity(i);

            }
        });


        change_step_target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this,ChangeStepTarget.class);
                startActivity(i);
            }
        });

        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this,Achievements.class);
                startActivity(i);
            }
        });

        contact_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this,ContactTrainer.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Update the isLoggedIn value to false as Logged Out
                SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false); // Set isLoggedIn to false
                editor.apply(); // Apply the changes

                Toast.makeText(Profile.this, "Successfully Logged Out!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(Profile.this, LoginPage.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });



        //bottom bar
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, HomePage.class);
                startActivity(i);

            }
        });

        meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, MealPlanning.class);
                startActivity(i);
                finish();
            }
        });

        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, WorkoutPlanning.class);
                startActivity(i);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Profile.this, Profile.class);
                startActivity(i);
                finish();
            }
        });
    }


    //to click picture
    public void captureImage(View view){

        //check for permission at runtime
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){

            //Request camera permission if not granted
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA_PERMISSION_CODE);
            return;
        }

        //open the camera app

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);

    }


    //to click picture
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode== RESULT_OK){

            //get the captured image as bitmap

            Bundle extras= data.getExtras();
            Bitmap imageBitmap= (Bitmap) extras.get("data");

            saveImageToGallery(imageBitmap);
        }
    }

    //to click picture and save in gallery
    private void saveImageToGallery(Bitmap imageBitmap){
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileName="IMG_"+timeStamp+".jpg";

        File imageFile= new File(storageDir,fileName);
        try {
            FileOutputStream outputStream= new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();

            Intent mediaScanIntent= new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(imageFile));
            sendBroadcast(mediaScanIntent);

            Toast.makeText(this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }
    }
}