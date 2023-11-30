package com.example.sdc_app.enrollment;

import android.view.View;

import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;

public class MyCourseHolder extends RecyclerView.ViewHolder {
    TextView courseName ;
    TextView courseOfferedBy;
    TextView courseRatings;

    public MyCourseHolder(View itemView) {
        super(itemView);
        courseName = itemView.findViewById(R.id.course_name);
        courseOfferedBy = itemView.findViewById(R.id.course_offered_by);
    }
}
