package com.example.sdc_app.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddCourse;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder>{
    Context context;
    private List<AddCourse> courseList;
    private OnItemClickListener listener;

    public void setFilteredList(List<AddCourse> filteredList){
        this.courseList=filteredList;
        notifyDataSetChanged();
    }
    public CourseAdapter(Context context,List<AddCourse> courseList,OnItemClickListener listener){
        this.courseList=courseList;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.course_layout,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        AddCourse course = courseList.get(position);
        holder.name.setText(course.getName());
        holder.offeredBy.setText(course.getOfferedBy());
        holder.courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(course);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(AddCourse item);
    }
    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView offeredBy;
        Button courseButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.course_name);
            offeredBy=itemView.findViewById(R.id.course_offered_by);
            courseButton=itemView.findViewById(R.id.explore_course_btn);
        }
    }
}
