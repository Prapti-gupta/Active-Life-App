package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private boolean passwordVisible = false;
    private boolean confPasswordVisible = false;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheme(R.style.Base_Theme_ActiveLife);

        // Initialize database helper
        dbHelper = new UserDatabaseHelper(this);

        // Accessing the views
        EditText nameInput = findViewById(R.id.name_input);
        EditText emailInput = findViewById(R.id.email_input);
        EditText passwordInput = findViewById(R.id.password_input);
        EditText confPassInput = findViewById(R.id.confpass_input);
        Button signUpButton = findViewById(R.id.signup_button);
        TextView clickableText = findViewById(R.id.login);

        // Toggle for password field
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

        // Toggle for confirm password field
        confPassInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (confPassInput.getRight() - confPassInput.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        if (confPasswordVisible) {
                            confPassInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            confPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eyepassword, 0);
                        } else {
                            confPassInput.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            confPassInput.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_open, 0);
                        }
                        confPasswordVisible = !confPasswordVisible;
                        return true;
                    }
                }
                return false;
            }
        });

        // Click listener for login text
        clickableText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginPage.class);
                startActivity(i);
            }
        });

        // Setting up click listener for the sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String confirmPassword = confPassInput.getText().toString().trim();
                CheckBox termsCheckbox = findViewById(R.id.terms_checkbox);

                // Validation logic
                if (name.isEmpty()) {
                    nameInput.requestFocus();
                    nameInput.setError("Enter your name");
                } else if (email.isEmpty()) {
                    emailInput.requestFocus();
                    emailInput.setError("Enter your email");
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.requestFocus();
                    emailInput.setError("Enter a valid email");
                } else if (password.isEmpty()) {
                    passwordInput.requestFocus();
                    passwordInput.setError("Enter a password");
                } else if (password.length() < 6) {
                    passwordInput.requestFocus();
                    passwordInput.setError("Password must be at least 6 characters");
                } else if (!confirmPassword.equals(password)) {
                    confPassInput.requestFocus();
                    confPassInput.setError("Passwords do not match");
                } else if (!termsCheckbox.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please accept the Privacy Policy and Terms of Use", Toast.LENGTH_SHORT).show();
                } else {

                        Intent i = new Intent(MainActivity.this, GetName.class);
                        i.putExtra("email",email);
                        i.putExtra("password",password);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
        });
    }
}
