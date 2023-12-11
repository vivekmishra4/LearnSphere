package com.example.sdc_app.enrollment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddCourse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnrollmentSection extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference database;
    DatabaseReference courseDatabase;
    FirebaseUser user;
    MyAdapter myAdapter;
    List<AddCourse> courseList;

    @Override
    public void onResume() {
        super.onResume();
    }

    public EnrollmentSection(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.enrollment_section,container,false);
        setAllThings(view);


        // Populate itemList with your data (image resource, text1, text2)

        myAdapter = new MyAdapter(courseList, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AddCourse course) {
                if(course!=null){
                    Intent intent=new Intent(getActivity(), CourseViewActivity.class);
                    intent.putExtra("courseId",course.getCourseId());
                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).commit();
                    getActivity().startActivity(intent);

                }

            }
        });
        recyclerView.setAdapter(myAdapter);

        //Setting User Course
        setUserCourses();
        setCourseChildListener();

        return view;
    }
    public void setAllThings(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        user= FirebaseAuth.getInstance().getCurrentUser();
        database=FirebaseDatabase.getInstance().getReference();
        courseDatabase=database.child("user/"+user.getUid()+"/courses");
        courseList = new ArrayList<>();

    }

    public void setUserCourses(){
        courseDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String courseId = dataSnapshot.getKey();
                    String status=dataSnapshot.getValue(String.class);
                    if ("pending".equals(status)) {
                        fetchUserCourse(courseId);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void setCourseChildListener(){
        courseDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.i("course changed","course changed");
                if(snapshot.getValue(String.class).equals("completed")){
                    String courseId = snapshot.getKey();

                    // Update local data (courseList) by removing the course with the matching courseId
                    Iterator<AddCourse> iterator = courseList.iterator();
                    while (iterator.hasNext()) {
                        AddCourse course = iterator.next();
                        if (course.getCourseId().equals(courseId)) {
                            iterator.remove();
                            break; // Assuming courseId is unique, so we can break after the first match
                        }
                    }

                    // Notify the adapter that the data has changed
                    if (myAdapter != null) {
                        myAdapter.setCourseList(courseList);
                    }

                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Log.i("course removed","course removed");
                String courseId = snapshot.getKey();

                // Update local data (courseList) by removing the course with the matching courseId
                Iterator<AddCourse> iterator = courseList.iterator();
                while (iterator.hasNext()) {
                    AddCourse course = iterator.next();
                    if (course.getCourseId().equals(courseId)) {
                        iterator.remove();
                        break; // Assuming courseId is unique, so we can break after the first match
                    }
                }

                // Notify the adapter that the data has changed
                if (myAdapter != null) {
                    myAdapter.setCourseList(courseList);
                }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void fetchUserCourse(String courseId) {
        DatabaseReference courseDatabase=database.child("course/"+courseId);
        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    AddCourse course=snapshot.getValue(AddCourse.class);
                    courseList.add(course);

                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
