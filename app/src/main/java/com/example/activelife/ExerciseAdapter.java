package com.example.activelife;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private ArrayList<String> exerciseList;
    private Context context;

    public ExerciseAdapter(ArrayList<String> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        String exerciseName = exerciseList.get(position);
        holder.exerciseNameTextView.setText(exerciseName);

        // Set click listener for the exercise item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExerciseDetailActivity.class);
            intent.putExtra("exerciseName", exerciseName); // Pass the exercise name or ID as needed
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseNameTextView;

        public ExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exercise_name_text_view); // Update with your layout ID
        }
    }
}
