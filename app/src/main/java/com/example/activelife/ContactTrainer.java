package com.example.activelife;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ContactTrainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_trainer);

        ImageButton back=(ImageButton) findViewById(R.id.bb1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ContactTrainer.this, Profile.class);
                startActivity(i);
            }
        });
    }

    // Male trainer button actions
    public void callmale(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:1234567890"));
        startActivity(callIntent);
    }

    public void messagemale(View view) {
        Intent messageIntent = new Intent(Intent.ACTION_VIEW);
        messageIntent.setData(Uri.parse("sms:1234567890"));
        messageIntent.putExtra("sms_body", "Hello, Trainer!");
        startActivity(messageIntent);
    }

    public void emailmale(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");  // Ensures only email apps handle the intent
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"male_trainer@example.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Training Inquiry");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I would like to know more about your training programs.");

        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }

    // Female trainer button actions
    public void callfemale(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:0987654321"));
        startActivity(callIntent);
    }

    public void messagefemale(View view) {
        Intent messageIntent = new Intent(Intent.ACTION_VIEW);
        messageIntent.setData(Uri.parse("sms:0987654321"));
        messageIntent.putExtra("sms_body", "Hello, Trainer!");
        startActivity(messageIntent);
    }

    public void emailfemale(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");  // Ensures only email apps handle the intent
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"female_trainer@example.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Training Inquiry");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I would like to know more about your training programs.");

        startActivity(Intent.createChooser(emailIntent, "Send email"));
    }

}
