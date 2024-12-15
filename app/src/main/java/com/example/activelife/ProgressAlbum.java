package com.example.activelife;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAlbum extends AppCompatActivity {

    ArrayList<Uri> imageUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_album);

        // Define the projection for the query
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_MODIFIED,
        };

        String selection = null;
        String[] selectionArgs = null;
        String orderBy = MediaStore.Images.Media.DATE_MODIFIED + " DESC"; // Order by date modified in descending order

        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(contentUri, projection, selection, selectionArgs, orderBy);

        if (cursor != null) {
            // Move to the first position
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    Uri imageUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    imageUris.add(imageUri);
                } while (cursor.moveToNext()); // Continue until there are no more items
            }
            cursor.close(); // Close the cursor after use

            // Set up the RecyclerView with a GridLayoutManager
            RecyclerView rv = findViewById(R.id.rv);
            rv.setLayoutManager(new GridLayoutManager(this, 3));
            PhotoAdapter adapter = new PhotoAdapter(imageUris);
            rv.setAdapter(adapter);
        }
    }
}
