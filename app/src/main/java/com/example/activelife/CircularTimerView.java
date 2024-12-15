package com.example.activelife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

public class CircularTimerView extends View {

    private Paint circlePaint;
    private Paint progressPaint;
    private Paint textPaint;
    private int totalTime = 15; // Default total time in seconds
    private int elapsedTime = 0; // Time elapsed since the start

    public CircularTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Paint for the circular border
        circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(context, R.color.dress)); // Set the color from colors.xml
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(20);

        // Paint for the progress arc
        progressPaint = new Paint();
        progressPaint.setColor(ContextCompat.getColor(context, R.color.darkgrey)); // Set color for progress
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(20);

        // Paint for the text in the center
        textPaint = new Paint();
        textPaint.setColor(ContextCompat.getColor(context, R.color.black)); // Set text color
        textPaint.setTextSize(90); // Set text size
        textPaint.setTextAlign(Paint.Align.CENTER);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2 - (int) circlePaint.getStrokeWidth();

        // Draw the circular border
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint);

        // Calculate sweep angle for progress arc based on elapsed time
        float sweepAngle = (360f * elapsedTime) / totalTime;

        // Draw the progress arc
        canvas.drawArc(
                width / 2 - radius,
                height / 2 - radius,
                width / 2 + radius,
                height / 2 + radius,
                -90,
                sweepAngle,
                false,
                progressPaint
        );

        // Draw remaining time text in the center of the circle
        int remainingTime = totalTime - elapsedTime;
        String timeText = String.valueOf(remainingTime);
        canvas.drawText(timeText, width / 2, height / 2 + (textPaint.getTextSize() / 3), textPaint);
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        this.elapsedTime = 0;
        invalidate(); // Redraw the view with the updated total time
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
        invalidate(); // Redraw the view with the updated elapsed time
    }

    public void startTimer() {
        elapsedTime = 0;
        invalidate();
    }

    public void stopTimer() {
        elapsedTime = totalTime; // Set elapsed time to total time to represent end of timer
        invalidate();
    }
}
