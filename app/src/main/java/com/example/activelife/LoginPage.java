package com.example.activelife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    private boolean passwordVisible = false;
    private boolean confPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        setTheme(R.style.Base_Theme_ActiveLife);

        // Accessing the views
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);
        Button signInButton = findViewById(R.id.signup_button);

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(this);


        passwordInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2; // Index for drawableRight
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (passwordInput.getRight() - passwordInput.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        if (passwordVisible) {
                            passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eyepassword, 0);
                        } else {
                            passwordInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0);
                        }
                        passwordVisible = !passwordVisible;
                        return true;
                    }
                }
                return false;
            }
        });


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fetching values from EditText fields
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                // Validation logic
                if (email.isEmpty()) {
                    emailInput.requestFocus();
                    emailInput.setError("Enter your email");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.matches("\\d{10}")) {
                    emailInput.requestFocus();
                    emailInput.setError("Enter a valid email or phone number");
                } else if (password.isEmpty()) {
                    passwordInput.requestFocus();
                    passwordInput.setError("Enter your password");
                } else {
                    // Check credentials
                    if (dbHelper.checkUserCredentials(email, password)) {
                        // If credentials are valid, get the userId
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        Cursor cursor = db.query(UserDatabaseHelper.TABLE_USER_CREDENTIALS, // Table name
                                new String[]{UserDatabaseHelper.COLUMN_USER_ID}, // Columns to retrieve
                                UserDatabaseHelper.COLUMN_USER_EMAIL + " = ?", // Where clause
                                new String[]{email}, // Where arguments
                                null, null, null); // Group by, having, order by

                        if (cursor != null && cursor.moveToFirst()) {
                            long userId = cursor.getLong(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USER_ID)); // Assuming userId is the column name

                            // Login success
                            Toast.makeText(LoginPage.this, "Sign-In Successful!", Toast.LENGTH_SHORT).show();

                            // Edit Shared Preference as Logged in
                            SharedPreferences sharedPreferences = getSharedPreferences("ActiveLifeLogin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true); // Save login status
                            editor.putLong("userId", userId); // Save userId
                            editor.apply();


                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        } else {
                            // This should not happen since we already checked credentials
                            Toast.makeText(LoginPage.this, "Error retrieving user ID.", Toast.LENGTH_SHORT).show();
                        }

                        if (cursor != null) {
                            cursor.close(); // Close the cursor to avoid memory leaks
                        }
                    } else {
                        // Login failed
                        Toast.makeText(LoginPage.this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
