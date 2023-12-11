package com.example.sdc_app.community;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CommunitySection extends Fragment {
    CommunityAdapter communityAdapter;
    RecyclerView recyclerView;
    List<CommunityItem> itemList;
    DatabaseReference databaseReference;
    FirebaseUser user;
    public CommunitySection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.community_section,container,false);

        //Setting All Fields
        setAllFields(view);

        //Getting all Communities
        getAllCommunities();

        return view;
    }
    public void setAllFields(View view){
        recyclerView=view.findViewById(R.id.community_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        itemList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        communityAdapter=new CommunityAdapter(getContext(), itemList, new CommunityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CommunityItem item) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(item.getCommunityLink()));
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(communityAdapter);

    }
    public void getAllCommunities(){
        DatabaseReference communityReference=databaseReference.child("user").child(user.getUid()).child("courses");
        communityReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String courseId=dataSnapshot.getKey();
                    getCommunity(courseId);
                }
                communityAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void getCommunity(String courseId){
        DatabaseReference communityReference=databaseReference.child("community").child(courseId);
        communityReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CommunityItem communityItem=snapshot.getValue(CommunityItem.class);
                itemList.add(communityItem);
                communityAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

