package com.example.sdc_app.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;


import java.util.List;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityHolder>{
    private List<CommunityItem> itemList;
    private OnItemClickListener listener;
    Context context;

    public CommunityAdapter( Context context,List<CommunityItem> itemList, OnItemClickListener listener) {
        this.context = context;
        this.itemList = itemList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public CommunityAdapter.CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.course_layout,parent,false);
        return new CommunityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.CommunityHolder holder, int position) {
        CommunityItem item=itemList.get(position);
        holder.name.setText(item.getName());
        holder.offeredBy.setText(item.getOfferedBy());
        holder.communityButton.setText("View Community");
        holder.communityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class CommunityHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView offeredBy;
        Button communityButton;

        public CommunityHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.course_name);
            offeredBy=itemView.findViewById(R.id.course_offered_by);
            communityButton=itemView.findViewById(R.id.explore_course_btn);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(CommunityItem item);
    }
}
