package com.example.sdc_app.explore;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;


public class CourseViewHolder extends RecyclerView.ViewHolder {
    TextView name;
    TextView offeredBy;

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.course_name);
        offeredBy=itemView.findViewById(R.id.course_offered_by);
    }
}
