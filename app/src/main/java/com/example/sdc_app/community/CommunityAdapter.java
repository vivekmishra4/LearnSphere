package com.example.sdc_app.community;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.CommunityHolder>{
    @NonNull
    @Override
    public CommunityAdapter.CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CommunityAdapter.CommunityHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CommunityHolder extends RecyclerView.ViewHolder {
        public CommunityHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
