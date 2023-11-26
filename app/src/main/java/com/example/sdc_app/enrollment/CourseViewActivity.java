package com.example.sdc_app.enrollment;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdc_app.R;
import com.example.sdc_app.profile.AddCourse;
import com.example.sdc_app.profile.AddTopic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CourseViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    DatabaseReference courseDatabase;
    List<AddTopic> topics;
    TextView courseName;
    TextView courseOfferedBy;
    CourseViewAdapter courseViewAdapter;
    AddCourse myCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        courseName=findViewById(R.id.my_course_name);
        courseOfferedBy=findViewById(R.id.my_course_offered_by);
        recyclerView=findViewById(R.id.list_of_topics);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        topics=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        courseViewAdapter=new CourseViewAdapter(topics, new CourseViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Button button) {
                if(button.getId()==R.id.topic_content_btn){
                    Toast.makeText(CourseViewActivity.this,"View Content was Clicked",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(CourseViewActivity.this,"Take Test was Clicked",Toast.LENGTH_LONG).show();

                }

            }
        });
        recyclerView.setAdapter(courseViewAdapter);
        setCourseDetails();



    }
    public void setAllIds(){

    }
    public void setCourseDetails(){
        String courseId= getIntent().getStringExtra("courseId");
        courseDatabase=databaseReference.child("course/"+courseId);
        courseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    myCourse=snapshot.getValue(AddCourse.class);
                    courseName.setText(myCourse.getName());
                    courseOfferedBy.setText(myCourse.getOfferedBy());
                    topics.addAll(myCourse.getTopics());
                    


                }

                courseViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
    public void setTopic(){

    }
}