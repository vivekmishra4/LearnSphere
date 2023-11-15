package com.example.sdc_app.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExploreSection extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database;
    ArrayList<MyCourse> courses;
    CourseAdapter courseAdapter;
    public ExploreSection(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.explore_section,container,false);
        recyclerView=view.findViewById(R.id.explore_recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        database= FirebaseDatabase.getInstance().getReference("courses");
        List<MyCourse> courseList=new ArrayList<>();

        courseAdapter=new CourseAdapter(getContext(), courseList, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyCourse item) {
                Toast.makeText(getContext(), "habibi", Toast.LENGTH_SHORT).show();

            }
        });
        recyclerView.setAdapter(courseAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyCourse course=dataSnapshot.getValue(MyCourse.class);
                    courseList.add(course);


                }
                courseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}
