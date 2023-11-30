package com.example.sdc_app.explore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseViewHolder> {
    Context context;
    private List<MyCourse> courseList;
    private OnItemClickListener listener;
    public CourseAdapter(Context context,List<MyCourse> courseList,OnItemClickListener listener){
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
        MyCourse course = courseList.get(position);
        holder.name.setText(course.getName());
        holder.offeredBy.setText(course.getOfferedBy());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        void onItemClick(MyCourse item);
    }
}
