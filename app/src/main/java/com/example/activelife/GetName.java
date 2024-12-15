package com.example.activelife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class GetName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_Theme_ActiveLife);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_name);

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");



        EditText nameEditText = findViewById(R.id.name);
        Button next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = nameEditText.getText().toString().trim(); // Trim the input to remove spaces

                // Validation logic
                if (Name.isEmpty()) {
                    nameEditText.requestFocus();
                    nameEditText.setError("Enter your Name");
                } else if (!Name.matches("[a-zA-Z ]+")) { // Allow spaces between alphabetic characters
                    nameEditText.requestFocus();
                    nameEditText.setError("Enter only Alphabets and Spaces");
                } else {
                    Intent i = new Intent(GetName.this, GetLocation.class);
                    i.putExtra("email", email);
                    i.putExtra("password",password);
                    i.putExtra("name", Name);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }
}