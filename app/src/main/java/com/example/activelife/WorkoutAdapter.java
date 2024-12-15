package com.example.activelife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private final Context context;
    private final ArrayList<Workout> workoutList;
    private final OnWorkoutClickListener listener; // Listener interface for clicks
    private final int layoutResource; // Variable for layout resource ID

    // Constructor accepting listener and layout resource
    public WorkoutAdapter(Context context, ArrayList<Workout> workoutList, OnWorkoutClickListener listener, int layoutResource) {
        this.context = context;
        this.workoutList = workoutList;
        this.listener = listener; // Assign listener
        this.layoutResource = layoutResource; // Initialize the layout resource
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout based on the passed layout resource
        View view = LayoutInflater.from(context).inflate(layoutResource, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        Workout workout = workoutList.get(position);
        holder.workoutName.setText(workout.getName());
        holder.workoutTime.setText(workout.getTime());
        holder.workoutImage.setImageResource(workout.getImageResId());

        // Set the click listener on the ImageButton
        holder.workoutImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWorkoutClick(workout); // Trigger the listener
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        TextView workoutName;
        TextView workoutTime;
        ImageButton workoutImage;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workout_name);
            workoutTime = itemView.findViewById(R.id.workout_time);
            workoutImage = itemView.findViewById(R.id.workout_image);
        }
    }

    // Interface for click listener
    public interface OnWorkoutClickListener {
        void onWorkoutClick(Workout workout);
    }
}
