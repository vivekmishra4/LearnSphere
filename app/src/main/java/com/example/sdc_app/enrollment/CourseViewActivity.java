package com.example.sdc_app.enrollment;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sdc_app.R;
import com.example.sdc_app.assessment.TestActivity;
import com.example.sdc_app.profile.AddCourse;
import com.example.sdc_app.profile.AddQuestion;
import com.example.sdc_app.profile.AddTopic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
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

        //Setting all fields
        setAllFields();

        //Set Adapter and listener for each ite,
        setAdapterWithListener();

        //Setting Course Details
        setCourseDetails();



    }
    public void setAllFields(){
        courseName=findViewById(R.id.my_course_name);
        courseOfferedBy=findViewById(R.id.my_course_offered_by);
        recyclerView=findViewById(R.id.list_of_topics);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        topics=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);

    }
    public void setAdapterWithListener(){
        courseViewAdapter=new CourseViewAdapter(topics, new CourseViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Button button, int position) {
                AddTopic topic=topics.get(position);
                String topicName=topic.getName();
                Toast.makeText(CourseViewActivity.this, "Clicked"+String.valueOf(position), Toast.LENGTH_SHORT).show();
                if(button.getId()==R.id.topic_test_btn){
                    List<AddQuestion> questions=topic.getTestQuestions();
                    Intent intent=new Intent(CourseViewActivity.this, TestActivity.class);
                    intent.putExtra("questions",(Serializable) questions);
                    intent.putExtra("topicName",topicName);
                    intent.putExtra("courseName",myCourse.getName());
                    intent.putExtra("courseId",myCourse.getCourseId());
                    intent.putExtra("topicId",String.valueOf(position));
                    intent.putExtra("courseOfferedBy",myCourse.getOfferedBy());
                    startActivity(intent);


                }
                else{
                    String contentLink=topic.getFileLink();
                    Intent intent=new Intent(CourseViewActivity.this, PDFActivity.class);
                    intent.putExtra("topicName",topicName);
                    intent.putExtra("contentLink",contentLink);
                    startActivity(intent);


                }

            }


        });
        recyclerView.setAdapter(courseViewAdapter);

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

}