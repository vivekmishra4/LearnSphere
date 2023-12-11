package com.example.sdc_app.assessment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class AssessmentSection extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database;
    DatabaseReference courseDatabase;
    FirebaseUser user;
    AssessmentCourseAdapter courseAdapter;
    List<AssessmentItem> itemsList;
    public AssessmentSection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.assessment_section,container,false);
        setAllFields(view);
        courseAdapter=new AssessmentCourseAdapter(itemsList, (imageView, courseId) -> {
            //public void onItemClick(ImageView imageView, String courseId)
            Log.i("a","assessment");
            courseDatabase.child(courseId).removeValue();
            database.child("user/"+user.getUid()+"/courses/"+courseId).removeValue();

        });

        recyclerView.setAdapter(courseAdapter);
        setUserCourses();
        return view;
    }
    public void setAllFields(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        user= FirebaseAuth.getInstance().getCurrentUser();
        database= FirebaseDatabase.getInstance().getReference();
        courseDatabase=database.child("enrollment/user/"+user.getUid()+"/completed");
        itemsList=new ArrayList<>();
    }
    public void setUserCourses(){
        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    AssessmentItem item=dataSnapshot.getValue(AssessmentItem.class);
                    itemsList.add(item);

                }
                courseAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

