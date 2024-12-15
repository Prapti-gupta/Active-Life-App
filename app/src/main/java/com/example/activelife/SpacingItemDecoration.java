package com.example.activelife;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public SpacingItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        outRect.bottom = space; // Set bottom spacing
    }
}

