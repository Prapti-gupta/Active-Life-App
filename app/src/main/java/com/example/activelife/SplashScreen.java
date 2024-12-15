package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

public class SplashScreen extends AppCompatActivity {

    Animation pop;
    ImageView logo;
    private TextView quoteTextView;
    private UserDatabaseHelper dbHelper;
    private List<String> quotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        pop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop);
        logo.setAnimation(pop);

        quoteTextView = findViewById(R.id.quoteTextView);

        // Initialize the SQLite database helper and load quotes
        dbHelper = new UserDatabaseHelper(this);
        quotesList = dbHelper.getAllQuotes();

        // Display a random quote from the SQLite database
        if (quotesList != null && !quotesList.isEmpty()) {
            displayRandomQuote();
        } else {
            quoteTextView.setText("Stay motivated!");
        }

        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            Intent intent = isLoggedIn ? new Intent(SplashScreen.this, HomePage.class) :
                    new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }

    private void displayRandomQuote() {
        Random random = new Random();
        int randomIndex = random.nextInt(quotesList.size());
        quoteTextView.setText("\"" +quotesList.get(randomIndex)+ "\"");
    }
}
