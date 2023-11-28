package com.example.sdc_app.enrollment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddTopic;


import java.util.List;

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.CourseViewHolder> {
    private List<AddTopic> topicList;
    private OnItemClickListener listener;
    public CourseViewAdapter(List<AddTopic> topicList,OnItemClickListener listener){
        this.topicList=topicList;
        this.listener=listener;
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_layout,parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        AddTopic topic=topicList.get(position);
        holder.topicName.setText(topic.getName());
        holder.takeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(holder.takeTest,holder.getAdapterPosition());
                }

            }
        });
        holder.contentLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClick(holder.contentLink,holder.getAdapterPosition());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView topicName;
        Button contentLink;
        Button takeTest;
        public CourseViewHolder(View itemView){
            super(itemView);
            topicName=itemView.findViewById(R.id.topic_name_text);
            contentLink=itemView.findViewById(R.id.topic_content_btn);
            takeTest=itemView.findViewById(R.id.topic_test_btn);


        }
    }
    public interface OnItemClickListener {
        void onItemClick(Button button,int position);
    }
}
